package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.ApplyException;
import org.junit.jupiter.api.*;

import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {

    private static Validator validator;

    private Mission normalTest;
    private Mission wriedTest;


    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @BeforeEach
    public void createNormalTest() {
        String email = "abcd@naver.com";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 100;
        Maker maker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
        long cash = 100000L;
        maker.cashToPoint(cash);
        LocalDate recruitmentTimeStart = LocalDate.now();
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);

        normalTest = Mission.newInstance(
                title,
                content,
                imageURL,
                point,
                limitApply,
                maker,
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );
    }

    @BeforeEach
    public void createWriedTest() {
        String email = "abcd@naver.com";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 100;
        Maker maker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
        long cash = 100000L;
        maker.cashToPoint(cash);
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(10L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);

        wriedTest = Mission.newInstance(
                title,
                content,
                imageURL,
                point,
                limitApply,
                maker,
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        );

    }

    @Test
    @DisplayName("테스트 생성 성공 테스트")
    public void createTestSuccessTest() {
        // given
        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        long cash = 100000L;
        long point = 100L;
        int limitApply = 100;
        long remainPoint = cash - point * limitApply;
        // when
        // then
        assertEquals(title, normalTest.getTitle());
        assertEquals(content, normalTest.getContent());
        assertEquals(remainPoint, normalTest.getMaker().getPoint());
    }

    @Test
    @DisplayName("테스트 생성 실패 테스트 - 제한인원이 0보다 작거나 같음")
    public void createTestFailTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcd1234";
        String nickname = "에이비씨";
        String phone = "010-1234-5678";
        String company = "카카오";

        String title = "보통 테스트입니다.";
        String content = "보통 테스트에 대한 내용입니다!";
        String imageURL = "ImageURLCheck";
        long point = 100L;
        int limitApply = 0;
        Maker maker = Maker.newInstance(
                email,
                password,
                nickname,
                phone,
                company
        );
        long cash = 100000L;
        maker.cashToPoint(cash);
        LocalDate recruitmentTimeStart = LocalDate.now().plusDays(5L);
        LocalDate recruitmentTimeEnd = LocalDate.now().plusDays(20L);
        LocalDate durationTimeStart = LocalDate.now().plusDays(30L);
        LocalDate durationTimeEnd = LocalDate.now().plusDays(50L);
        assertThrows(ApplyException.class, () -> Mission.newInstance(
                title,
                content,
                imageURL,
                point,
                limitApply,
                maker,
                recruitmentTimeStart,
                recruitmentTimeEnd,
                durationTimeStart,
                durationTimeEnd
        ));
    }

}