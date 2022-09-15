package io.wisoft.testermatchingplatform.handler.aop;

import io.wisoft.testermatchingplatform.jwt.JwtStorage;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class RoleCheckAop {

    private final JwtStorage jwtStorage;
    @Pointcut("execution(* io.wisoft.testermatchingplatform.web.controller..*.*(..))")
    private void cut(){

    }

    @Before("cut()")
    public void check(){
        if (jwtStorage.getToken() != null) {
            System.out.println(jwtStorage.getToken());
            jwtStorage.getToken().get("roles");
        }
    }
}
