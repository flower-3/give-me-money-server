package com.givememoney.controller;

import com.givememoney.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void kakoCallback(String code, HttpServletResponse response) throws IOException {
        String jwtToken = authService.getJwtByKakaoCode(code);
        Cookie cookie = new Cookie("Bearer", jwtToken);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect(clientUri);
    }
}
