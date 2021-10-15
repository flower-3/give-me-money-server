package com.givememoney.entity;

import com.givememoney.constant.AgeRange;
import com.givememoney.constant.MedianIncome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 128, nullable = false)
    private String name;
    private String password;
    private String role;
    @NotNull
    private String email;
    private String provider;
    private String gender;
    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private MedianIncome medianIncome;
    private boolean isSick;
    private boolean isPregnant;
    private boolean isWorker;
    private boolean isFarmer;
    private boolean hasMultiChildren;


}
