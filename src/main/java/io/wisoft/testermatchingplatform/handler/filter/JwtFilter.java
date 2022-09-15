package io.wisoft.testermatchingplatform.handler.filter;

import io.jsonwebtoken.Claims;
import io.wisoft.testermatchingplatform.jwt.JwtStorage;
import io.wisoft.testermatchingplatform.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtStorage jwtStorage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("filter start");
        String jwt = resolveToken(request);


        // Token 유효성 검사
        if (StringUtils.hasText(jwt) && jwtTokenProvider.isValidToken(jwt)) {
            jwtStorage.setToken(jwtTokenProvider.getTokenData(jwt));
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
