package com.givememoney.dto.government.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GovernmentServiceListRequestDto {
    private int page;
    private int perPage;

}
