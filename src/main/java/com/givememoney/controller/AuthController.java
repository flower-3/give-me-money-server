package com.givememoney.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.givememoney.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${oauth.jwt.redirect-host}")
    private String redirectUrl;
    @Value("${uri.client}")
    private String clientUri;

    @GetMapping("/kakao/callback")
    public RedirectView kakoCallback(String code, HttpServletResponse response) throws JsonProcessingException {
        String jwtToken = authService.getJwtByKakaoCode(code);
        Cookie cookie = new Cookie("Bearer", jwtToken);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        RedirectView redirectView = new RedirectView(clientUri);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }
}