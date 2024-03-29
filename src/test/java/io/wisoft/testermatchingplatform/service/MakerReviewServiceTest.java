package io.wisoft.testermatchingplatform.service;

import io.wisoft.testermatchingplatform.domain.ApplyInformation;
import io.wisoft.testermatchingplatform.domain.MakerReview;
import io.wisoft.testermatchingplatform.repository.ApplyInformationRepository;
import io.wisoft.testermatchingplatform.repository.MakerReviewRepository;
import io.wisoft.testermatchingplatform.web.dto.request.CreateMakerReviewRequest;
import io.wisoft.testermatchingplatform.web.dto.response.CreateMakerReviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakerReviewServiceTest {

    @Mock
    private MakerReviewRepository makerReviewRepository;
    @Mock
    private ApplyInformationRepository applyInformationRepository;
    private MakerReviewService makerReviewService;

    @BeforeEach
    public void prepareTest() {
        makerReviewService = new MakerReviewService(makerReviewRepository, applyInformationRepository);
    }

    @Test
    public void createMakerReviewSuccessTest() throws Exception {
        //given
        UUID makerId = UUID.randomUUID();
        UUID applyInformationId = UUID.randomUUID();
        int starPoint = 3;
        String testComment = "test Comment";
        CreateMakerReviewRequest request = CreateMakerReviewRequest.newInstance(starPoint, testComment);

        ApplyInformation mockApplyInformation = mock(ApplyInformation.class);
        when(applyInformationRepository.findById(applyInformationId)).thenReturn(Optional.ofNullable(mockApplyInformation));

        UUID makerReviewId = UUID.randomUUID();
        MakerReview mockMakerReview = mock(MakerReview.class);
        when(mockMakerReview.getId()).thenReturn(makerReviewId);

        when(makerReviewRepository.save(any(MakerReview.class))).thenReturn(mockMakerReview);

        //when
        CreateMakerReviewResponse response = makerReviewService.createMakerReview(applyInformationId, request);

        //then
        assertEquals(makerReviewId, response.getMakerReviewId());
    }


}