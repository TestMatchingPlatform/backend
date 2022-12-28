package io.wisoft.testermatchingplatform.domain;

import io.wisoft.testermatchingplatform.handler.exception.LoginException;
import io.wisoft.testermatchingplatform.handler.exception.PointException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;


import static org.junit.jupiter.api.Assertions.*;

class TesterTest {

    private Tester normalTester;
    private Tester wriedTester;

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    public void createNormalTester() {
        String email = "abcd@naver.com";
        String password = "abcdef12345";
        String nickname = "kkukjun";
        String phone = "010-2243-1252";
        String introduce = "안녕하세요 저는 이국준이예요.";

        normalTester = Tester.newInstance(
                email,
                password,
                nickname,
                phone,
                introduce
        );
    }

    @BeforeEach
    public void createWriedTester() {
        String email = "abcdaver.com";
        String password = "abcdef12345";
        String nickname = "kkukjun";
        String phone = "010-2243-1252";
        String introduce = "안녕하세요 저는 이국준이예요.";

        wriedTester = Tester.newInstance(
                email,
                password,
                nickname,
                phone,
                introduce
        );
    }

    @Test
    @DisplayName("테스터 생성 성공 테스트")
    public void createTesterSuccessTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcdef12345";

        // when, then
        assertEquals(email, normalTester.getEmail());
        assertEquals(password, normalTester.getPassword());

    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcdef12345";

        // when, then
        assertDoesNotThrow(() -> normalTester.login(email, password));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 아이디 비밀번호 불일치")
    public void loginFailTest() {
        // given
        String email = "abcd@naver.com";
        String password = "abcd12345";

        // when, then
        assertThrows(LoginException.class, () -> normalTester.login(email, password));
    }

    @Test
    @DisplayName("보상 수령 성공 테스트")
    public void rewardPointSuccessTest() {
        // given
        long point = 1000L;

        // when, then
        assertDoesNotThrow(()->normalTester.rewardPoint(point));
    }

    @Test
    @DisplayName("보상 수령 실패 테스트 - 포인트 0보다 작거나 같은 경우")
    public void rewardPointFailTest() {
        // given
        long point = -120L;

        // when, then
        assertThrows(PointException.class, () -> normalTester.rewardPoint(point));
    }

    @Test
    @DisplayName("계좌로 포인트 이동 성공 테스트")
    public void depositPointSuccessTest() {
        //given
        long rewardPoint = 10000L;
        long depositPoint = 5000L;
        normalTester.rewardPoint(rewardPoint);
        long remainPoint = rewardPoint - depositPoint;

        // when, then
        assertDoesNotThrow(() -> normalTester.pointToCash(depositPoint));
        assertEquals(remainPoint, normalTester.getPoint());
    }

    @Test
    @DisplayName("계좌로 포인트 이동 실패 테스트 - 잔액보다 그 이상의 포인트 이동")
    public void depositPointFailTest() {
        //given
        long rewardPoint = 1000L;
        long depositPoint = 5000L;
        normalTester.rewardPoint(rewardPoint);

        // when, then
        assertThrows(PointException.class, () -> normalTester.pointToCash(depositPoint));
    }




}