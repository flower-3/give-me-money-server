package com.givememoney.controller;

import com.givememoney.dto.common.ResponseDto;
import com.givememoney.dto.user.UserResponseDto;
import com.givememoney.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseDto getUserDetails(HttpServletRequest httpServletRequest){
        String userPk = httpServletRequest.getAttribute("userId").toString(); // 시큐리티 필터에서 세팅된 userId
        UserResponseDto userResponseDto = userService.getUserDetails(userPk);
        return ResponseDto.builder()
                .messsage("success")
                .data(userResponseDto)
                .status(HttpStatus.OK)
                .build();
    }
}
