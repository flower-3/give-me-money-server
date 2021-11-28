package com.givememoney.dto.user;

import com.givememoney.constant.MedianIncome;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class UserResponseDto {
    private Long id;
    @NotNull
    private String userId;
    private String name;
    private String password;
    private String role;
    private String email;
    private String provider;
    private String gender;
    private String birthyear;
    private String birthday;
    private String phoneNumber;
    private MedianIncome medianIncome;
    private boolean isSick;
    private boolean isPregnant;
    private boolean isWorker;
    private boolean isFarmer;
    private boolean hasMultiChildren;
}
