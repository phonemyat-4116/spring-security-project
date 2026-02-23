package com.codesnippet.weather_service.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collectors;

@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Add condition
        //if(authentication != null && authentication.isAuthenticated() && "anonymousUser".equals(authentication.getName())) {  // sometimes Spring auto create unauthenticated users this name
        if(!"anonymousUser".equals(authentication.getName())){
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(auth -> auth.startsWith("ROLE_"))
                    .collect(Collectors.joining(", "));

            System.out.println("Request URL : " + httpServletRequest.getRequestURL() + ". Accessed by " + roles);

        }
        else{
            System.out.println("Request URL : " + httpServletRequest.getRequestURL());
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}


/**
 * SecurityContext
 *     └── Authentication (UsernamePasswordAuthenticationToken)
 *             ├── Principal → Users object
 *             ├── Credentials → null
 *             ├── Authorities → [ROLE_ADMIN, WEATHER_READ, WEATHER_WRITE]
 *             └── Authenticated → true
 *
 *
 *  Authentication {
 *     principal = Users(username=lisa, role=ADMIN)
 *     authorities = [
 *         SimpleGrantedAuthority("ROLE_ADMIN"),
 *         SimpleGrantedAuthority("WEATHER_READ"),
 *         SimpleGrantedAuthority("WEATHER_WRITE")
 *     ]
 *     authenticated = true
 * }
 */