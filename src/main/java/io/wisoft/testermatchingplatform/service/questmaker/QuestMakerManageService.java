package io.wisoft.testermatchingplatform.service.questmaker;

import io.wisoft.testermatchingplatform.domain.apply.Apply;
import io.wisoft.testermatchingplatform.domain.apply.ApplyRepository;
import io.wisoft.testermatchingplatform.domain.category.Category;
import io.wisoft.testermatchingplatform.domain.category.CategoryRepository;
import io.wisoft.testermatchingplatform.domain.quest.Quest;
import io.wisoft.testermatchingplatform.domain.quest.QuestRepository;
import io.wisoft.testermatchingplatform.domain.quest.QuestRelateTime;
import io.wisoft.testermatchingplatform.domain.questmaker.QuestMaker;
import io.wisoft.testermatchingplatform.domain.questmaker.QuestMakerRepository;
import io.wisoft.testermatchingplatform.domain.submit.Submit;
import io.wisoft.testermatchingplatform.domain.submit.SubmitRepository;
import io.wisoft.testermatchingplatform.domain.tasksubmit.TaskSubmit;
import io.wisoft.testermatchingplatform.domain.tasksubmit.TaskSubmitRepository;
import io.wisoft.testermatchingplatform.handler.exception.category.CategoryNotFoundException;
import io.wisoft.testermatchingplatform.handler.exception.questmaker.QuestMakerNotFoundException;
import io.wisoft.testermatchingplatform.web.dto.req.apply.ApproveRequest;
import io.wisoft.testermatchingplatform.web.dto.req.quest.QuestRegistRequest;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyIdResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterDetailResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.apply.ApplyTesterListResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.quest.QuestIdResponse;
import io.wisoft.testermatchingplatform.web.dto.resp.submit.SubmitTesterListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestMakerManageService {

    private final QuestMakerRepository questMakerRepository;
    private final CategoryRepository categoryRepository;
    private final QuestRepository questRepository;

    private final ApplyRepository applyRepository;

    private final SubmitRepository submitRepository;

    private final TaskSubmitRepository taskSubmitRepository;

    // 퀘스트 등록
    @Transactional
    public QuestIdResponse registQuest(final QuestRegistRequest request, final Long questMakerId) {

        Category category = categoryRepository.findByName(request.getCategoryName()).orElseThrow(
                () -> new CategoryNotFoundException("category not found")
        );
        QuestMaker questMaker = questMakerRepository.findById(questMakerId).orElseThrow(
                () -> new QuestMakerNotFoundException("questmaker not found")
        );

        Quest quest = new Quest(
                request.getTitle(),
                request.getContent(),
                category,
                new QuestRelateTime(request.getRecruitmentTimeStart(),
                        request.getRecruitmentTimeLimit(),
                        request.getDurationTimeStart(),
                        request.getDurationTimeLimit(),
                        request.getModifyTimeStart(),
                        request.getModifyTimeLimit()),
                questMaker,
                request.getParticipantCapacity(),
                request.getReward(),
                request.getRequireCondition(),
                request.getPreferenceCondition()
        );

        quest = questRepository.save(quest);
        return QuestIdResponse.from(quest);
    }

    // 퀘스트 삭제
    @Transactional
    public void deleteQuest(final Long questId) {
        questRepository.deleteById(questId);
    }

    // 퀘스트 수정
    @Transactional
    public QuestIdResponse updateQuest(final Long questMakerId, final Long questId, final QuestRegistRequest request) {
        Category category = categoryRepository.findByName(request.getCategoryName()).orElseThrow(
                () -> new CategoryNotFoundException("category not found")
        );
        QuestMaker questMaker = questMakerRepository.findById(questMakerId).orElseThrow(
                () -> new QuestMakerNotFoundException("questmaker not found")
        );

        Quest quest = new Quest(
                questId,
                request.getTitle(),
                request.getContent(),
                category,
                new QuestRelateTime(request.getRecruitmentTimeStart(),
                        request.getRecruitmentTimeLimit(),
                        request.getDurationTimeStart(),
                        request.getDurationTimeLimit(),
                        request.getModifyTimeStart(),
                        request.getModifyTimeLimit()),
                questMaker,
                request.getParticipantCapacity(),
                request.getReward(),
                request.getRequireCondition(),
                request.getPreferenceCondition()
        );

        quest = questRepository.save(quest);
        return QuestIdResponse.from(quest);

    }

    // 퀘스트 신청자 조회
    @Transactional
    public Page<ApplyTesterListResponse> selectQuestApplyTester(final Long questId) {
        Pageable pageable = PageRequest.of(0,50);
        return applyRepository.findByQuest_Id(questId,pageable)
                .map(ApplyTesterListResponse::from);
    }

    // 퀘스트 신청자 세부 조회
    @Transactional
    public ApplyTesterDetailResponse selectApplyTesterDetail(final Long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(
                // 예외 처리
        );
        return ApplyTesterDetailResponse.from(apply);
    }

    // 퀘스트 신청자 승인
    @Transactional
    public ApplyIdResponse applyTesterApprove(final Long applyId, final ApproveRequest approveRequest) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(

        );
        if (approveRequest.getSuccess() == 1) {
            apply.setPermissionTime(new Timestamp(System.currentTimeMillis()));
            apply = applyRepository.save(apply);
        }
        return ApplyIdResponse.from(apply);
    }


}
