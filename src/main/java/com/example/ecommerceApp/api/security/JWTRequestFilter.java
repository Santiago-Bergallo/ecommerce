package com.example.ecommerceApp.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.ecommerceApp.model.LocalUser;
import com.example.ecommerceApp.model.dao.LocalUserDao;
import com.example.ecommerceApp.service.JwtService;
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
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    LocalUserDao localUserDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
           String token = tokenHeader.substring(7);
           try {
               String username = jwtService.getUserName(token);
               Optional<LocalUser> opuser = localUserDao.findByUsernameIgnoreCase(username);
               if (opuser.isPresent()) {
                   LocalUser user = opuser.get();
                   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }

           } catch (JWTDecodeException ex) {

           }
        }

        filterChain.doFilter(request, response);
    }
}
