package com.givememoney.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.givememoney.dto.kakao.KakaoUserResponseDto;
import com.givememoney.dto.kakao.OAuthTokenRequestDto;
import com.givememoney.entity.User;
import com.givememoney.jwt.TokenProvider;
import com.givememoney.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Value("${oauth.kakao.client_id}")
    private String kakaoClientId;
    @Value("${oauth.kakao.client_secret}")
    private String kakaoClientSecret;
    @Value("${oauth.kakao.auth_uri}")
    private String kakaoAuthUri;
    @Value("${oauth.kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("유저 이름을 찾을 수 없습니다."));
        return user;
    }

    /*
     * accessToken 받아와서 카카오 유저정보 받아와 UserEntity생성 및 로그인 처리
     * */
    public String getJwtByKakaoCode(String code) throws JsonProcessingException {
        OAuthTokenRequestDto oAuthTokenRequestDto = getAccessToken(code);
        KakaoUserResponseDto kakaoUserResponseDto = getKakaoUserInformation(oAuthTokenRequestDto);
        userService.kakaoOauthUserUpsert(oAuthTokenRequestDto, kakaoUserResponseDto);
        return tokenProvider.createToken(String.valueOf(kakaoUserResponseDto.getId()));
    }

    /*
    * GET/POST /v2/user/me HTTP/1.1
      Host: kapi.kakao.com
      Authorization: Bearer {ACCESS_TOKEN}
      Content-type: application/x-www-form-urlencoded;charset=utf-8
    * */
    private KakaoUserResponseDto getKakaoUserInformation(OAuthTokenRequestDto oAuthTokenRequestDto) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, String> requestBodys = new LinkedMultiValueMap<>();

        requestHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        requestHeaders.add("Host", "kapi.kakao.com");
        requestHeaders.add("Authorization", "Bearer " + oAuthTokenRequestDto.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(requestBodys, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(responseEntity.getBody(), KakaoUserResponseDto.class);
    }

    private OAuthTokenRequestDto getAccessToken(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, String> requestBodys = new LinkedMultiValueMap<>();

        requestHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        requestHeaders.add("Host", "kauth.kakao.com");
        requestBodys.add("grant_type", "authorization_code");
        requestBodys.add("client_id", kakaoClientId);
        requestBodys.add("redirect_uri", kakaoRedirectUri);
        requestBodys.add("code", code);
        requestBodys.add("client_secret", kakaoClientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(requestBodys, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                kakaoAuthUri + "/oauth/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(responseEntity.getBody(), OAuthTokenRequestDto.class);
    }

    public String getSHA256Pwd(String rawPwd) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(rawPwd.getBytes());
        String hashedPwd = String.format("%064x", new BigInteger(1, md.digest()));
        return hashedPwd;
    }

}
