package com.leon.authentication.utils;

import com.leon.authentication.entity.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

public class JwtUtils {
    /*
    * @description:
                 私钥加密token
    * @date 2020/4/9 13:45
    * @param userInfo 载荷中的数据
    * @param privateKey 私钥
    * @param expireMinutes 过期时间，单位秒
    * @return java.lang.String
    */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstaints.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstaints.JWT_KEY_USER_NAME, userInfo.getUsername())
                //
                .setExpiration(DateTime.now().plusDays(expireMinutes).toDate())
                //使用指定的密钥对使用指定的算法构造的JWT进行签名，生成JWS。
                .signWith(SignatureAlgorithm.RS256, privateKey)
                //实际构建JWT并根据JWT紧凑序列化规则将其序列化为紧凑的url安全字符串
                .compact();
    }

    /*
    * @description:
                只有私钥字节数组的时候加密token
    * @date 2020/4/9 14:57
    * @param userInfo 载荷中的数据
	* @param privateKey 私钥字节数组
	* @param expireMinutes 过期时间，单位秒
    * @return java.lang.String
    */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstaints.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstaints.JWT_KEY_USER_NAME, userInfo.getUsername())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.ES256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /*
    * @description:
                公钥解析token
    * @date 2020/4/9 15:00
    * @param token 用户请求中的token
	* @param publicKey 公钥
    * @return io.jsonwebtoken.Jws<io.jsonwebtoken.Claims>
    */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static Jws<Claims> parserToken(String token, byte[] publicKey) {
//        return Jwts.parser().setSigningKey(RsaUtils.getPrivateKey(publicKey)).parseClaimsJws(token);
        //其实JwtParser中已经实现了直接读取key的字节数组
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /*
    * @description:
                获取token中的用户信息
    * @date 2020/4/9 16:26
    * @param token
	* @param publicKey
    * @return com.leon.entity.UserInfo
    */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) {
        //parserToken已经重载，自动选择了PublicKey参数的那个方法
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstaints.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstaints.JWT_KEY_USER_NAME))
        );
    }

    /*
    * @description:
                重载上述"获取token中的用户信息"的方法
    * @date 2020/4/9 16:32
    * @param token
	* @param publicKey
    * @return com.leon.entity.UserInfo
    */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) {
        //parserToken已经重载，自动选择了字节数组参数的那个方法
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstaints.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstaints.JWT_KEY_USER_NAME))
        );
    }
}
