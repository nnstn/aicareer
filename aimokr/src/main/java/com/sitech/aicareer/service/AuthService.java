package com.sitech.aicareer.service;

import com.sitech.aicareer.pojo.User;
import com.sitech.aicareer.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Resource
    UserService userService;

    public User login(User user){

        String password = user.getPassword();
        List<User> userList = userService.getUserByUserCode(user.getUserCode());
        if(1!=userList.size()){
            throw new RuntimeException("输入用户信息有误");
        }
        user = userList.get(0);
        password = PasswordUtil.getMD5String(password+user.getSalt());
        if(!user.getPassword().equals(password)){
            throw  new RuntimeException("用户密码错误");
        }

        // 保护用户密码和加密盐
        user.setPassword("");
        user.setSalt("");

        return user;
    }

    /**
     * 用户注册
     */
    public int registerUser(User user){
        if(null!=userService.getUserByUserCode(user.getUserName()) && userService.getUserByUserCode(user.getUserName()).size()>0){
            throw new RuntimeException("用户已存在");
        }

        if(null==user.getLeader()){
            user.setLeader(0L);
        }
        String salt = getSalt();
        user.setSalt(salt);
        user.setPassword(PasswordUtil.getMD5String(user.getPassword()+salt));

        return userService.insertUser(user);
    }

    /**
     * 获得16位随机数盐
     */
    private String getSalt(){
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder saltBuilder = new StringBuilder(16);
        saltBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = saltBuilder.length();

        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                saltBuilder.append("0");
            }
        }
        // 生成最终的加密盐
        return saltBuilder.toString();
    }

    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        System.out.println(isEmail("wangjn_bj@si-tech.com.cn"));
        System.out.println(isEmail("wangjn_bj.cn"));
    }
}
