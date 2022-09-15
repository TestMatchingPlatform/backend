package io.wisoft.testermatchingplatform.jwt;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
public class JwtStorage {

    HashMap<String, Claims> hashMap;

    public void setToken(Claims jwtToken){
        hashMap.put("token",jwtToken);
    }

    public Claims getToken(){
        return hashMap.get("token");
    }
}
