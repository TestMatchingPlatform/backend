package io.wisoft.testermatchingplatform.web.dto.resp.tester;

import lombok.Getter;

@Getter
public class TesterSignInResponse {
    private Long id;
    private String email;

    private String token;


    public TesterSignInResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static TesterSignInResponse from(final Long id, final String email){
        return new TesterSignInResponse(
                id,
                email
        );
    }

    public void setToken(String token) {
        this.token = token;
    }
}
