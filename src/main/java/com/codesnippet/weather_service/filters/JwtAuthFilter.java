package com.codesnippet.weather_service.filters;

import com.codesnippet.weather_service.service.CustomUserDetailService;
import com.codesnippet.weather_service.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTUtil jWTUtil;
    CustomUserDetailService customUserDetailService;

    public JwtAuthFilter(JWTUtil jWTUtil, CustomUserDetailService customUserDetailService) {
        this.jWTUtil = jWTUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1️⃣ Read Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // 2️⃣ Check if header exists and starts with "Bearer "
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);

            // Extract username (subject) from token
            username = jWTUtil.extractUsername(token);
        }

        // 3️⃣ If username exists AND user is not already authenticated
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // TODO fetch user by username
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username); // Load user details from database using username

            // TODO Validate Token
            // 4️⃣ Validate token (username match + expiration check)
            if(jWTUtil.validateToken(username, userDetails, token)){

                // Create Authentication object for Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, // Principe
                                null, // don’t need the password anymore. JWT already proved identity.
                                userDetails.getAuthorities() // roles
                        );
                // Attach request details (IP, session, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // TODO set to spring context
                // 5️⃣ Store authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }

        String path = request.getServletPath();

        // Skip JWT for H2 console
//        if (path.startsWith("/h2-console")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // Always continue filter chain
        filterChain.doFilter(request, response);



    }
}
