package io.wisoft.testermatchingplatform.web.controller.tester;

import io.wisoft.testermatchingplatform.handler.validator.image.ValidationSequence;
import io.wisoft.testermatchingplatform.web.jwt.JwtTokenProvider;
import io.wisoft.testermatchingplatform.service.tester.TesterManageService;
import io.wisoft.testermatchingplatform.service.tester.TesterAuthService;
import io.wisoft.testermatchingplatform.web.dto.req.tester.QuestApplyRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignInRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterSignUpRequest;
import io.wisoft.testermatchingplatform.web.dto.req.tester.TesterUpdateRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.tester.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TesterController {

    final TesterAuthService testerAuthService;
    final TesterManageService testerManageService;
    final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/testers")
    public ResponseEntity<SignUpResponse> registerTester(
            @ModelAttribute
            @Validated(ValidationSequence.class) final TesterSignUpRequest testerSignUpRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerManageService.signUp(testerSignUpRequest));

    }

//    연관 내용 전부 지워야 함.
//    @DeleteMapping("testers/{tester_id}")
//    public ResponseEntity deleteTester(@PathVariable("tester_id") Long testerId) {
//        testerManageService.deleteTester(testerId);
//        return ResponseEntity
//                .noContent().build();
//    }

    @PostMapping("/testers/login")
    public ResponseEntity<TesterSignInResponse> loginTester(
            @RequestBody @Valid final TesterSignInRequest testerSignInRequest
    ) {
        String prefix = "Bearer ";
        TesterSignInResponse response = testerAuthService.loginTester(testerSignInRequest);
//        String jwtToken = prefix + jwtTokenProvider.createJwtAccessToken(response.getId(),"tester");
//        response.setAccessToken(jwtToken);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    @PostMapping("/testers/{tester_id}/apply")
    public ResponseEntity<QuestApplyResponse> applyQuest(
            @PathVariable("tester_id") Long testerId,
            @ModelAttribute QuestApplyRequest questApplyRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerAuthService.applyQuest(questApplyRequest, testerId));

    }

    @GetMapping("/testers/{tester_id}/apply")
    public ResponseEntity<Page<QuestApplyListResponse>> showApplyQuests(
             @PathVariable("tester_id") Long testerId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testerAuthService.findApplyList(testerId));
    }


    @GetMapping("/testers/{tester_id}")
    public DetailTesterResponse getTesterInformation(@PathVariable("tester_id") Long testerId) {
        return testerManageService.findByTesterId(testerId);
    }

    @PatchMapping("/testers/{tester_id}")
    public ResponseEntity<TesterUpdateResponse> updateTester(
             @PathVariable("tester_id") Long testerId,
            @ModelAttribute TesterUpdateRequest testerUpdateRequest
    ) {
         return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(testerAuthService.updateTester(testerUpdateRequest, testerId));
    }


}
