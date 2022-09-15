package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.Getter;

@Getter
public class TesterSignInResponse {
    private Long id;
    private String token;


    public TesterSignInResponse(Long id) {
        this.id = id;
    }

    public static TesterSignInResponse from(final Long id){
        return new TesterSignInResponse(
                id
        );
    }

    public void setToken(String token) {
        this.token = token;
    }
}
