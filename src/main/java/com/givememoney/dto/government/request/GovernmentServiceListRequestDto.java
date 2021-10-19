package com.givememoney.dto.government.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class GovernmentServiceListRequestDto {
    @NotNull(message = "page값은 필수입니다.")
    private int page;
    @NotNull(message = "perPage값은 필수입니다.")
    private int perPage;

}
