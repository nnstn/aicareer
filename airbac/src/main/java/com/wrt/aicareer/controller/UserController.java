package com.wrt.aicareer.controller;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.base.Captcha;
import com.wrt.aicareer.bean.LoginCodeEnum;
import com.wrt.aicareer.bean.LoginProperties;
import com.wrt.aicareer.bean.SecurityProperties;
import com.wrt.aicareer.component.RedisUtils;
import com.wrt.aicareer.component.TokenProvider;
import com.wrt.aicareer.config.RsaProperties;
import com.wrt.aicareer.dto.AuthUserDto;
import com.wrt.aicareer.dto.JwtUserDto;
import com.wrt.aicareer.po.RbacUser;
import com.wrt.aicareer.power.annotation.rest.AnonymousDeleteMapping;
import com.wrt.aicareer.power.annotation.rest.AnonymousPostMapping;
import com.wrt.aicareer.power.bean.JsonData;
import com.wrt.aicareer.power.exception.BadRequestException;
import com.wrt.aicareer.power.utils.RsaUtils;
import com.wrt.aicareer.service.OnlineUserService;
import com.wrt.aicareer.service.RbacUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : wangjn_bj
 * @date : 2021/7/4 21:52
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    RbacUserService rbacUserService;
    @Resource
    private LoginProperties loginProperties;

    @PostMapping("/register")
    public JsonData register(@RequestBody RbacUser user){
        log.info("进入用户注册方法");
        if(StringUtils.isBlank(user.getUsername())){
            return JsonData.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getTelephone())){
            return JsonData.fail("手机号不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return JsonData.fail("密码不能为空");
        }
        if(StringUtils.isBlank(user.getEmail())){
            return JsonData.fail("邮箱不能为空");
        }
        return rbacUserService.register(user);
    }
    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public JsonData login(@RequestBody RbacUser user) throws Exception{
        log.info("进入用户登录方法");
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, user.getPassword());
        // 查询验证码
        String code = (String) redisUtils.get(user.getUserId()+"");
        // 清除验证码
        redisUtils.del(user.getUserId()+"");

        if(StringUtils.isBlank(user.getUsername())){
            return JsonData.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return JsonData.fail("密码不能为空");
        }
        return rbacUserService.login(user);
    }

    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // 生成令牌与第三方系统获取令牌方式
//        // UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());
//        // Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        // SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = tokenProvider.createToken(authentication);
//        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
//        // 保存在线信息
//        onlineUserService.save(jwtUserDto, token, request);
//        // 返回 token 与 用户信息
//        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
//            put("token", properties.getTokenStartWith() + token);
//            put("user", jwtUserDto);
//        }};
//        if (loginProperties.isSingleLogin()) {
//            //踢掉之前已经登录的token
//            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
//        }
        return ResponseEntity.ok(null);
    }


    @ApiOperation("获取验证码")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }
    @ApiOperation("退出登录")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static void main(String[] args) {
        System.out.println(ResponseEntity.ok(new RbacUser()).toString());
    }
}
