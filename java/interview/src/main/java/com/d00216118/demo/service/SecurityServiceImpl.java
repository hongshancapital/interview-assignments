package com.d00216118.demo.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.security.Key;
import java.util.Date;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 8:09 下午 2021/3/29
 **/
@Service
public class SecurityServiceImpl implements SecurityService {

    public static final String secretKey = "8C8kum6LxyKWYLM78sKdXrwbTjDCFywU";

    /**
     * @param subject   the name of user
     * @param ttlMillis this is the expiration time,
     * @return the string value of JWT
     */
    @Override
    @Cacheable(value = "token")
    public String createToken(String subject, long ttlMillis) {

        if (ttlMillis <= 0) {
            throw new RuntimeException(" the param of ttlMillis must not null in createToken()");
        }

        if (!StringUtils.hasLength(subject)) {
            throw new IllegalArgumentException("subject name can't be blank/empty/null");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        builder.setExpiration(new Date(nowMillis + ttlMillis));

        return builder.compact();
    }


    @Override
    public String getSubject(@NotBlank String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token).getBody();

            return claims.getSubject();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }

    @Override
    public boolean checkUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
