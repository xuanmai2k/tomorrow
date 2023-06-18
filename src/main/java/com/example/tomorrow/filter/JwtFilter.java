package com.example.tomorrow.filter;

import com.example.tomorrow.ddd.auth.application.AuthApplication;
import com.example.tomorrow.ddd.auth.model.Account;
import com.example.tomorrow.jwt.JWTUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// https://blog.iamprafful.com/spring-boot-rest-api-authentication-best-practices-using-jwt-2022
// https://gist.github.com/OmarElgabry/bfbbbe5d082e003ce1f007c5b8b513ac
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthApplication authApplication;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;
        //package com.example.tomorrow.utils.TokenUtils --> giống
        if(null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            Claims claims = Jwts.parser().setSigningKey("xuanmai0906").parseClaimsJws(token).getBody();
            userName = claims.get("phone").toString();
        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            Account userDetails = authApplication.findByPhoneNumber(userName);

            try {
                if(jwtUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails((javax.servlet.http.HttpServletRequest) httpServletRequest) //?
                    );

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception e) {
                logger.error("Token expired on account: " + userDetails.getName() + " with phone number: " + userDetails.getPhone());
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
