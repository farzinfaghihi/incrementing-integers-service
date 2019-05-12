package com.farzinfaghihi.thinkific.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.farzinfaghihi.thinkific.v1.user.User;
import com.farzinfaghihi.thinkific.v1.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A custom filter for validating JWT Tokens passed in for Authorization.
 * This filter will be a part of the Spring Security chain, which will run the filter
 * before every endpoint configured with security.
 */
public class JWTAuthorizationFilter extends GenericFilterBean {

    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Lazily load the user service, as we cannot inject it directly as the filter is ran before Spring sets up all dependencies
        if (userService == null) {
            ServletContext servletContext = servletRequest.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(UserService.class);
        }

        // Cast the servlet request to the http request, so we can access the header
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("Authorization");
        // If the correct Authorization header is not present, do nothing and continue the Spring filter chain of calls
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = header.replace("Bearer ", "");
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (!token.isEmpty()) {
            // Parse the JWT token, and check if a user exists by the id
            String stringId = JWT.require(Algorithm.HMAC512("blueberry".getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
            Long id = Long.valueOf(stringId);
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                return new UsernamePasswordAuthenticationToken(user.get(), null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
