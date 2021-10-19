package com.givememoney.repository;

import com.givememoney.entity.OAuthToken;
import com.givememoney.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    User user;
    OAuthToken oAuthToken;
    @BeforeEach
    void setUp() {
        user = User.builder()
                        .userId("1")
                        .name("name")
                        .provider("kakao")
                        .birthday("1017")
                        .email("123@naver.com")
                        .gender("M")
                        .birthyear("20211018")
                        .phoneNumber("01021312103")
                        .password("123")
                        .build();

        oAuthToken = OAuthToken.builder()
                                .accessToken("123")
                                .refreshToken("1234")
                                .tokenType("1")
                                .refreshTokenExpiresIn(12345)
                                .expiresIn(123144)
                                .scope("12345")
                                .user(user)
                                .build();
    }

    @Transactional
    @Test
    public void 유저_데이터_생성테스트(){
        long id = testEntityManager.persistAndGetId(user,Long.class);
        testEntityManager.flush();
        User testUser = testEntityManager.find(User.class,id);
        assert "name".equals(testUser.getName());
        assert testUser.getCreatedAt().compareTo(LocalDateTime.now())<1 == true;
    }

    @Transactional
    @Test
    public void 토큰을통한_유저_생성테스트(){
        long id = testEntityManager.persistAndGetId(oAuthToken,Long.class);
        testEntityManager.persist(user);
        testEntityManager.flush();
        OAuthToken testOAuthToken = testEntityManager.find(OAuthToken.class,id);
        assert testOAuthToken.getUpdatedAt().compareTo(LocalDateTime.now())<1 == true;
    }


}
