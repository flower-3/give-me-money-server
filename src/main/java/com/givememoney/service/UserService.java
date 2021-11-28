package com.givememoney.service;

import com.givememoney.dto.kakao.KakaoUserResponseDto;
import com.givememoney.dto.kakao.OAuthTokenRequestDto;
import com.givememoney.dto.user.UserResponseDto;
import com.givememoney.entity.OAuthToken;
import com.givememoney.entity.User;
import com.givememoney.repository.OAuthTokenRepository;
import com.givememoney.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final OAuthTokenRepository oAuthTokenRepository;

    @Transactional
    public void kakaoOauthUserUpsert(OAuthTokenRequestDto oAuthTokenRequestDto, KakaoUserResponseDto kakaoUserResponseDto) {
        userRepository.findByUserIdAndProvider(String.valueOf(kakaoUserResponseDto.getId()), "kakao")
                .ifPresentOrElse((existingUser) -> updateOriginUser(existingUser, kakaoUserResponseDto, oAuthTokenRequestDto), new Runnable() {
                    @Override
                    public void run() {
                        User user = kakaoUserResponseDto.toEntity();
                        OAuthToken oAuthToken = oAuthTokenRequestDto.toEntity();
                        oAuthToken.connectsUser(user);
                        oAuthTokenRepository.save(oAuthToken);
                    }
                });
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserDetails(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("유저가 존재하지 않습니다."));
        return user.toUserDto();
    }

    private void updateOriginUser(User originUser, KakaoUserResponseDto newUser, OAuthTokenRequestDto newOAuthToken) {
        originUser.updateKakaoUser(newUser);
        OAuthToken originToken = oAuthTokenRepository.findByUser(originUser);
        originToken.updateToken(newOAuthToken);
    }


}
