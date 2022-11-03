package io.wisoft.testermatchingplatform.jwt.filter;

import io.jsonwebtoken.Claims;
import io.wisoft.testermatchingplatform.jwt.JwtAuthException;
import io.wisoft.testermatchingplatform.handler.exception.tester.TesterNotLoginException;
import io.wisoft.testermatchingplatform.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@WebFilter(value = "/testers/*")
public class JwtTesterTokenCheckFilter extends OncePerRequestFilter {


    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그인과 회원 가입 예외 처리
        if (request.getRequestURI().equals("/testers/login")) {
        } else if (request.getRequestURI().equals("/testers/register")) {
        } else {
            // access Token 만료시
            String accessToken = resolveAccessToken(request);
            if (!jwtProvider.isValidToken(accessToken)) {
                throw new TesterNotLoginException("세션이 만료돼 로그아웃 됨");
            } else {
                // 토큰의 정보 확인
                if (!jwtProvider.getTokenData(accessToken).get("roles").equals("tester")) {
                    throw new JwtAuthException("유효한 접근이 아님");
                }
                // access token 성공시
                Claims accessTokenData = jwtProvider.getTokenData(accessToken);
                UUID id = UUID.fromString(String.valueOf(accessTokenData.getSubject()));
                // 토큰 재발급
                accessToken = jwtProvider.createJwtAccessToken(id, "tester");
            }
            // client에 전송할 header setting
            response.setHeader(ACCESS_TOKEN, BEARER_PREFIX + accessToken);
        }
        filterChain.doFilter(request, response);
    }

    // prefix 자르기
    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}