package com.givememoney.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.givememoney.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoUserResponseDto {
    long id;
    String connectedAt;
    KakaoAccountDto kakaoAccount;

    public User toEntity(){
        return User.builder()
                    .userId(String.valueOf(id))
                    .provider("kakao")
                    .gender(Optional.ofNullable(kakaoAccount.getGender()).orElse(""))
                    .birthyear(Optional.ofNullable(kakaoAccount.getBirthyear()).orElse(""))
                    .email(Optional.ofNullable(kakaoAccount.getEmail()).orElse(""))
                    .birthday(Optional.ofNullable(kakaoAccount.getBirthyear()).orElse(""))
                    .build();

    }
}
