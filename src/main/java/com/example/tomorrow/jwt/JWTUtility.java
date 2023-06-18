package com.example.tomorrow.jwt;

import com.example.tomorrow.ddd.auth.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//https://www.tabnine.com/code/java/classes/com.goblin.security.JwtTokenUtil
public class JWTUtility implements Serializable {
    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;// giây //Hằng số này được sử dụng để thiết lập thời lượng thời gian mà một JWT sẽ hợp lệ.
    @Value("${jwt.secret}")
    private String secretKey;
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public String getPhoneNumberFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get("phone").toString();
    }
    //validate token
    public Boolean validateToken(String token, Account account) {
        final String phone = getPhoneNumberFromToken(token);
        return (phone.equals(account.getPhone()) && !isTokenExpired(token));
    }
    public String generateToken(Account userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userDetails.getName());
        claims.put("role", userDetails.getRole());
        claims.put("address", userDetails.getAddress());
        claims.put("email",userDetails.getEmail());
        claims.put("status", userDetails.getStatus());
        claims.put("phone",userDetails.getPhone());
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100000000))//thời gian hết hạn của token
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();//thuật toán mã hóa được sử dụng là HS512 (HMAC-SHA512)
    }
}
