package io.wisoft.testermatchingplatform.web.controller.questmaker;

import io.wisoft.testermatchingplatform.jwt.JwtStorage;
import io.wisoft.testermatchingplatform.service.questmaker.QuestMakerManageService;
import io.wisoft.testermatchingplatform.web.dto.req.apply.ApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.req.quest.QuestRegistRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyIdResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterDetailResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterListResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.quest.QuestIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/questMakers")
public class QuestMakerManageController {

    private final QuestMakerManageService questMakerManageService;

    private final JwtStorage jwtStorage;

    @PostMapping("/quests")
    public ResponseEntity<QuestIdResponse> registQuest(@Validated @RequestBody final QuestRegistRequest request) {
        QuestIdResponse response = questMakerManageService.registQuest(request, Long.parseLong(jwtStorage.getToken().getSubject()));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/quests")
    public ResponseEntity deleteQuest(@RequestParam final Long quest_id) {
        questMakerManageService.deleteQuest(quest_id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/quests")
    public ResponseEntity<QuestIdResponse> updateQuest(@RequestParam final Long quest_id,@Validated @RequestBody final QuestRegistRequest request) {
        QuestIdResponse response = questMakerManageService.updateQuest(Long.parseLong(jwtStorage.getToken().getSubject()), quest_id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/quests/{questId}/applies")
    public ResponseEntity<Page<ApplyTesterListResponse>> applyTesterList(
            @PathVariable final Long questId
    ) {
         Page<ApplyTesterListResponse> responseList = questMakerManageService.selectQuestApplyTester(questId);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/quests/applies")
    public ResponseEntity<ApplyTesterDetailResponse> applyTesterDetail(
            @RequestParam final Long apply_id
    ) {
         ApplyTesterDetailResponse response = questMakerManageService.selectApplyTesterDetail(apply_id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/quests/applies")
    public ResponseEntity<ApplyIdResponse> applyTesterApprove(
            @RequestParam final Long apply_id,
            @Validated @RequestBody final ApproveRequest approveRequest
    ) {
         ApplyIdResponse response = questMakerManageService.applyTesterApprove(apply_id,approveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
