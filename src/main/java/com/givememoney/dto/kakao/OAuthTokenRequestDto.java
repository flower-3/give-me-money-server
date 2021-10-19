package com.givememoney.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.givememoney.entity.OAuthToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OAuthTokenRequestDto {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private int refreshTokenExpiresIn;

    public OAuthToken toEntity() {
        return OAuthToken.builder()
                .accessToken(this.accessToken)
                .tokenType(this.tokenType)
                .refreshToken(this.refreshToken)
                .expiresIn(this.expiresIn)
                .scope(this.scope)
                .refreshTokenExpiresIn(this.refreshTokenExpiresIn)
                .build();
    }
}