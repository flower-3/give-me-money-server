package com.givememoney.service;

import com.givememoney.entity.User;
import com.givememoney.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReadTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    public void 테스트_데이터_생성() {
        userRepository.deleteAll();
        IntStream.range(0, 50000).mapToObj(String::valueOf)
                                .forEach(userId ->
                                        testEntityManager.persist(
                                                User.builder()
                                                        .userId(userId)
                                                        .name("name")
                                                        .provider("local")
                                                        .email("name@gmail.com")
                                                        .build()));
        testEntityManager.flush();
        testEntityManager.clear(); //영속성 컨텍스트 삭제
    }

    @Test
    public void 조회시간측정테스트_어노테이션_없다(){
        userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Test
    public void 조회시간측정테스트_어노테이션_있다(){
        userRepository.findAll();
    }
}
