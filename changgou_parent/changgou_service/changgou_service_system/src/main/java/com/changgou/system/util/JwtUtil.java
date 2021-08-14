package com.changgou.system.util;

import com.sun.org.apache.regexp.internal.RE;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
//生成token的工具类
public class JwtUtil {
    private static final long JWT_TTL = 60*60*1000;
    private static final String JWT_KEY = "cast";
        public static String creatJwt(String id,String subject,Long ttlMills){
            SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;//对称加密
            long nowTimeMills = System.currentTimeMillis();
            Date date = new Date(nowTimeMills);
            SecretKey secretKey = generalKey();

            if (ttlMills==null){
                 ttlMills = JwtUtil.JWT_TTL;
            }
            long expirationTime = nowTimeMills + ttlMills;
            Date date1 = new Date(expirationTime);
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setId(id)//唯一id
                    .setSubject(subject)//主题
                    .setIssuer("admin")//签发作者
                      .setIssuedAt(date)//签发时间
                      .setExpiration(date1)//过期时间
                      .signWith(hs256,secretKey);//第一个参数对称加密，第二个参数密钥
            return jwtBuilder.compact();
        }
        public static SecretKey generalKey(){
            byte[] decode = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
            SecretKey secretKeySpec = new SecretKeySpec(decode,0,decode.length,"AE5");
            return secretKeySpec;
        }
    /**
     * 解析
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
