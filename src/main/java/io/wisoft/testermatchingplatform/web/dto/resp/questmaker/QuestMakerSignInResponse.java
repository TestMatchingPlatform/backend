package io.wisoft.testermatchingplatform.web.dto.resp.questmaker;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestMakerSignInResponse {

    private Long id;
    private String email;
    private String token;

    private QuestMakerSignInResponse(final Long id,final String email){
        this.id = id;
        this.email = email;
    }

    public static QuestMakerSignInResponse from(final Long id, final String email) {
        return new QuestMakerSignInResponse(id, email);
    }

    private void setToken(String token){
        this.token = token;
    }

}
