package com.givememoney.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoAccountDto {
    boolean emailNeedsAgreement;
    boolean isEmailValid;
    boolean isEmailVerified;
    String email;
    boolean birthyearNeedsAgreement;
    String birthyear;
    boolean birthdayNeedsAgreement;
    String birthday;
    boolean genderNeedsAgreement;
    String gender;
    boolean phoneNumberNeedsAgreement;
    String phoneNumber;

}
