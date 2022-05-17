package com.lia.system.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Component
public class Jwt {

    /**
     * Authorization字符串过期时间
     */
    @Value("${token.expireTime}")
    private int expireTime;
    @Value("${token.signature}")
    private String signature;


    /**
     * 生成Authorization字符串
     * @param map 需要携带的参数
     * @return Authorization字符串
     */
    public String getToken(Map map){
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime*60*1000))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
    }


    /**
     * 将Authorization字符串解析，获取字符串内携带的参数
     * @param token Authorization字符串
     * @return 携带的参数
     */
    public Map parse(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        return claimsJws.getBody();
    }

}
