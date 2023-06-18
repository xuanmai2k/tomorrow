package com.example.tomorrow.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    public String getFieldValueThroughToken(HttpServletRequest request, String field_name) {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if(null != authorization && authorization.startsWith("Bearer ")) { // chuỗi cố định được sử dụng trong JSON Web Tokens (JWT), phân biệt giữa phần header và phần payload của JWT, nếu chuỗi authorization không bắt đầu bằng "Bearer ", thì biến token sẽ giữ giá trị null
            token = authorization.substring(7);//cắt bỏ 7 ký tự đầu tiên của chuỗi authorization, để lấy phần thân của JWT
        }
        Claims claims = Jwts.parser().setSigningKey("xuanmai0906").parseClaimsJws(token).getBody();
        return claims.get(field_name).toString();
    }
}
