package com.givememoney.dto.government.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GovernmentServiceListResponseDto {
    private int currentCount;
    private List<GovernmentServiceResponseDto> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;
}
