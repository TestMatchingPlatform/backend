package io.wisoft.testermatchingplatform.web.controller.questmaker;

import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerTokenResponse;
import io.wisoft.testermatchingplatform.web.jwt.JwtTokenProvider;
import io.wisoft.testermatchingplatform.service.questmaker.QuestMakerAuthService;
import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSigninRequest;
import io.wisoft.testermatchingplatform.web.dto.req.questmaker.QuestMakerSignupRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignInResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.questmaker.QuestMakerSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/questMakers")
public class QuestMakerAuthController {

    private final QuestMakerAuthService questMakerAuthService;

    private final JwtTokenProvider jwtTokenProvider;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<QuestMakerSignUpResponse> signupQuestMaker(@Validated @RequestBody QuestMakerSignupRequest request){
        // 사용자 등록
        QuestMakerSignUpResponse response = this.questMakerAuthService.signupQuestMaker(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 회원 탈퇴
    @DeleteMapping("/remove")
    public ResponseEntity deleteQuestMaker(){
        // 사용자 삭제 ->  구현 예정

        return ResponseEntity.noContent().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<QuestMakerTokenResponse> loginQuestMaker(
            @Validated
            @RequestBody QuestMakerSigninRequest request
    ){
        String prefix = "Bearer ";
        QuestMakerSignInResponse response = this.questMakerAuthService.loginQuestMaker(request);

        // db,client -> refresh token
        String refreshToken = prefix + jwtTokenProvider.createJwtRefreshToken(response.getId());
        questMakerAuthService.setRefreshToken(response.getId(), refreshToken);

        // client -> access token
        String accessToken = prefix + jwtTokenProvider.createJwtAccessToken(
                response.getId(),"questMaker");

        QuestMakerTokenResponse questMakerTokenResponse = new QuestMakerTokenResponse();
        questMakerTokenResponse.setId(response.getId());
        questMakerTokenResponse.setAccessToken(accessToken);
        questMakerTokenResponse.setRefreshToken(refreshToken);

        return ResponseEntity.ok().body(questMakerTokenResponse);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity logoutQuestMaker(
            HttpServletRequest httpServletRequest
            ){
        // 세션 삭제
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
