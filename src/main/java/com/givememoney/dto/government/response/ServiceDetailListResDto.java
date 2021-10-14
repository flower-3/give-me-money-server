package com.givememoney.dto.government.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ServiceDetailListResDto {
    private int currentCount;
    private List<ServiceDetailResDto> data;
    private int matchCount;
    private int page;
    private int perPage;
    private int totalCount;
}
