package com.givememoney.entity;

import com.givememoney.constant.MedianIncome;
import com.givememoney.dto.kakao.KakaoAccountDto;
import com.givememoney.dto.kakao.KakaoUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseTime implements UserDetails {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "user_id")
    @NotNull
    private String userId;
    @Column(name = "provider")
    @NotNull
    private String provider;
    private String name;
    private String password;
    private String role;
    private String email;
    private String gender;
    private String birthday;
    private String birthyear;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private MedianIncome medianIncome;
    private boolean isSick;
    private boolean isPregnant;
    private boolean isWorker;
    private boolean isFarmer;
    private boolean hasMultiChildren;


    public void updateKakaoUser(KakaoUserResponseDto user) {
        KakaoAccountDto kakaoAccountDto = user.getKakaoAccount();
        this.provider = "kakao";
        this.email = Optional.ofNullable(kakaoAccountDto.getEmail()).orElse("");
        this.gender = Optional.ofNullable(kakaoAccountDto.getGender()).orElse("");
        this.birthyear = Optional.ofNullable(kakaoAccountDto.getBirthyear()).orElse("");
        this.birthday = Optional.ofNullable(kakaoAccountDto.getBirthday()).orElse("");
        this.phoneNumber = Optional.ofNullable(kakaoAccountDto.getPhoneNumber()).orElse("");

    }

    @PrePersist
    public void perPersist() {
        this.provider = Optional.ofNullable(this.provider).orElse("local");
        this.role = Optional.ofNullable(this.role).orElse("public");
        this.password = Optional.ofNullable(this.password).orElse("");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role)); // role 일단 전부 1로 통일
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
