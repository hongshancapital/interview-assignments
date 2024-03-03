package com.shorturl.util;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * token 工具类
 * @author shaochengming
 * @date 2021/10/15
 */
public class SignUtil {
	/**
     * token私钥
     */
    private static final String TOKEN_SECRET = "e36e507c28163d0e855e72c0a6a0e316";
    
    /**
     * @param token 密钥
     * @return boolean 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    
    /**
     * @param posturl 
     * @return 加密的token
     */
    public static String sign(String posturl) {
        try {
            //私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //用参数信息生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("posturl", posturl)
                    .sign(algorithm);
        } catch (Exception exception) {
            return null;
        }
    } 
}
