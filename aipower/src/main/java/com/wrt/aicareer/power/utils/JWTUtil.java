package com.wrt.aicareer.power.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 
 * @author KOF
 *
 */
public class JWTUtil {

	/** token秘钥，请勿泄露，请勿随便修改,备份：ysiWmldGNKf2.083na[2r */
	public static final String SECRET = "ysiWmldGNKf2.083na[2r";
	// 签发者
	public static final String SIGN_USER = "AICAREER";
	// claim中自定义负载信息时使用的key
	public static final String KEY_IP_RANGE = "IP_RANGE";
	public static final String KEY_CHECK_CODE = "CHECK_CODE";

	/**
	 * 生成jwt token
	 * @param expTime token失效时间
	 * @param payloadMap 自定义的负载信息
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(Date expTime, Map<String, String> payloadMap) throws Exception {
		// 0 参数校验
		if (expTime == null) {
			throw new Exception("过期时间不能为空");
		}

		Builder jwtBuilder = JWT.create();

		// 1 初始化header参数
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("alg", "HS256"); // 声明加密的算法
		headerMap.put("typ", "JWT"); // 声明类型
		jwtBuilder = jwtBuilder.withHeader(headerMap);

		// 2 处理payload
		// 2-1 加载标准声明信息
		jwtBuilder = jwtBuilder.withIssuer(SIGN_USER).withIssuedAt(new Date()) // sign time
				.withExpiresAt(expTime); // expire time

		// 2-2 加载公共声明信息
		if (payloadMap != null && payloadMap.size() > 0) {
			for (Map.Entry<String, String> entry : payloadMap.entrySet()) {
				String mapKey = entry.getKey();
				String mapValue = entry.getValue();
				jwtBuilder = jwtBuilder.withClaim(mapKey, mapValue);
			}
		}

		// 3 生成token
		String token = jwtBuilder.sign(Algorithm.HMAC256(SECRET));

		return token;
	}

	/**
	 * 为AI定制的token生成方法
	 * @param expTime token失效时间
	 * @param ipRange 可访问的IP范围，格式为正则表达式
	 * @param checkCode 用户提供的校验码
	 * @return
	 * @throws Exception
	 */
	public static String generateTokenForSpecial(Date expTime, String ipRange, String checkCode) throws Exception {
		// 0 参数校验
		if (expTime == null) {
			throw new Exception("过期时间不能为空");
		}

		if (ipRange == null || "".equals(ipRange)) {
			throw new Exception("可访问的IP范围不能为空");
		}

		if (checkCode == null || "".equals(checkCode)) {
			throw new Exception("校验码不能为空");
		}

		// 1 生成token
		Map<String, String> payloadMap = new HashMap<String, String>();
		payloadMap.put(KEY_IP_RANGE, ipRange);
		payloadMap.put(KEY_CHECK_CODE, checkCode);

		String token = generateToken(expTime, payloadMap);

		return token;
	}

	/**
	 * 获取负载信息中的ip range值
	 * @param claims
	 * @return
	 * @throws Exception
	 */
	public static String getCheckCode(Map<String, Claim> claims) throws Exception {
        Claim checkCodeClaim = claims.get(KEY_CHECK_CODE);
        if (null == checkCodeClaim || "".equals(checkCodeClaim.asString())) {
        	throw new Exception("get checkCode fail.");
        }
        return checkCodeClaim.asString();
	}
	
	/**
	 * 获取负载信息中的check code值
	 * @param claims
	 * @return
	 * @throws Exception
	 */
	public static String getIpRange(Map<String, Claim> claims) throws Exception {
        Claim ipRangeClaim = claims.get(KEY_IP_RANGE);
        if (null == ipRangeClaim || "".equals(ipRangeClaim.asString())) {
        	throw new Exception("get  fail.");
        }
        return ipRangeClaim.asString();
	}
	
	/**
	 * 
	 * 校验token是否有效，如果有效，则返回负载信息
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) throws Exception {
		DecodedJWT jwt = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			jwt = verifier.verify(token);
		} catch (Exception e) {
			e.printStackTrace();
			// token 校验失败, 抛出Token验证非法异常
			throw new Exception("jwt token check fail.");
		}
		return jwt.getClaims();
	}

	public static void main1(String[] args) throws Exception {
        // 0 生成token的例子
		Calendar nowTime = Calendar.getInstance();
//		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//	System.out.print("nowTime:"+sdf1.format(nowTime.getTime())+"\n" );
     //   nowTime.add(Calendar.MINUTE, 60);
        nowTime.add(Calendar.SECOND, 4);
	//	System.out.print("nowTime+10:"+sdf1.format(nowTime.getTime())+"\n");
        Date expiresDate = nowTime.getTime();
	//	System.out.print("expiresDate:"+sdf1.format(expiresDate)+"\n");
		String token = JWTUtil.generateTokenForSpecial(expiresDate, "172.21.3.*", "ghyujk");
	//	String token = JWTUtil.generateTokenForAI(expiresDate, "172.21.3.*", "ghyujk");
	//	System.out.println(token);
		
		// 1 校验token是否有效,主要校验失效日期
	//	String tokentemp = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJUF9SQU5HRSI6IjE3Mi4yMS4zLioiLCJDSEVDS19DT0RFIjoiZ2h5dWprIiwiaXNzIjoiSUFTUCIsImV4cCI6MTYwOTMyMjE0NiwiaWF0IjoxNjA5MzE4NTQ2fQ";
		Map<String, Claim> claims = null;
		try {
			claims = JWTUtil.verifyToken(token); // 实际应用中，用户的请求信息中会带有checkCode、token串
		} catch (Exception e) {
			throw new Exception("权限校验异常");
		}
		
		// 2 如果有效，则获取申请时提供的可访问ip范围、校验码
		if(null != claims){
			String ipRange = JWTUtil.getIpRange(claims);
			String checkCode = JWTUtil.getCheckCode(claims);
			// 3 业务逻辑处理
			// 从请求信息中获取访问ip、校验码，分别与ipRange、checkCode做比较，校验是否通过。

			System.out.println(ipRange);
			System.out.println(checkCode);

		}

	}

}
