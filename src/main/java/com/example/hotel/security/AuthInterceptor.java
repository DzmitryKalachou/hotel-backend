package com.example.hotel.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hotel.errors.exceptions.UserNotExistsException;
import com.example.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";


    private final UserService userService;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.getInstance()
            .clean();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //No need to use it.
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeaderText = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(authorizationHeaderText)) {
            final String token = authorizationHeaderText.replace(TOKEN_PREFIX, StringUtils.EMPTY);
            final String subject = verifyTokenAndGetUserId(token);
            final Long userId = Long.valueOf(subject);
            if (!userService.isUserExists(userId)) {
                throw new UserNotExistsException();
            }
            final SecurityContextHolder instance = SecurityContextHolder.getInstance();
            instance.putUserId(userId);
            instance.putToken(token);
        }
        return true;
    }

    private String verifyTokenAndGetUserId(final String token) {
        return JWT.require(Algorithm.HMAC512(jwtSecret.getBytes()))
            .build()
            .verify(token)
            .getSubject();
    }

}
