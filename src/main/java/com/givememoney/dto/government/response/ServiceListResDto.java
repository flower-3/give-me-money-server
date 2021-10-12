package com.givememoney.dto.government.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ServiceListResDto {
    private int currentCount;
    private List<ServiceResDto> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;
}
