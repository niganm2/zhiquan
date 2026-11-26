package com.yule.app.gateway.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppJwtUtil {

    // TOKEN的有效期一天（S）
    private static final int TOKEN_TIME_OUT = 3_600;
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    // 最小刷新间隔(S)
    private static final int REFRESH_TIME = 300;

    // 签名密钥，由 KEY 派生，长度满足 HS256（>=256bit）要求
    private static final SecretKey SIGN_KEY = Keys.hmacShaKeyFor(TOKEN_ENCRY_KEY.getBytes(StandardCharsets.UTF_8));

    // 生产ID
    public static String getToken(Long id) {
        Map<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id", id);
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date(currentTime))  //签发时间
                .subject("system")  //说明
                .issuer("yule") //签发者信息
                .audience().add("app").and()  //接收用户
                .compressWith(Jwts.ZIP.GZIP)  //数据压缩方式
                .signWith(SIGN_KEY) //加密方式，由密钥长度自动推导（HS256）
                .expiration(new Date(currentTime + TOKEN_TIME_OUT * 1000L))  //过期时间戳
                .claims(claimMaps) //cla信息
                .compact();
    }

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .verifyWith(SIGN_KEY)
                .build()
                .parseSignedClaims(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getPayload();
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if (claims == null) {
            return 1;
        }
        try {
            // 需要自动刷新TOKEN
            if ((claims.getExpiration().getTime() - System.currentTimeMillis()) > REFRESH_TIME * 1000L) {
                return -1;
            } else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        return SIGN_KEY;
    }

    public static void main(String[] args) {
        String token = AppJwtUtil.getToken(1102L);
        System.out.println(token);
        Claims claims = AppJwtUtil.getClaimsBody(token);
        System.out.println(claims.get("id"));
        System.out.println(AppJwtUtil.verifyToken(claims));
    }

}
