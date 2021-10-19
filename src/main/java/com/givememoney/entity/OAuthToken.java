package com.givememoney.entity;

import com.givememoney.dto.kakao.OAuthTokenRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OAuthToken {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private int refreshTokenExpiresIn;
    @UpdateTimestamp
    //@LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateToken(OAuthTokenRequestDto oAuthToken) {
        this.accessToken = oAuthToken.getAccessToken();
        this.tokenType = oAuthToken.getTokenType();
        this.refreshToken = oAuthToken.getRefreshToken();
        this.expiresIn = oAuthToken.getExpiresIn();
        this.scope = oAuthToken.getScope();
        this.refreshTokenExpiresIn = oAuthToken.getRefreshTokenExpiresIn();
    }

    public void connectsUser(User user) {
        this.user = user;
    }
}
