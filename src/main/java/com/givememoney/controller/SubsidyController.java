package com.givememoney.controller;

import com.givememoney.dto.common.ResponseDto;
import com.givememoney.dto.government.request.GovernmentServiceListRequestDto;
import com.givememoney.dto.government.response.ServiceDetailListResDto;
import com.givememoney.dto.government.response.ServiceListResDto;
import com.givememoney.service.SubsidyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubsidyController {

    private final SubsidyService subsidyService;

    @GetMapping("/gov24/v1/serviceList")
    public ResponseDto getServiceList(int page, int perPage) {

        GovernmentServiceListRequestDto requestDto = new GovernmentServiceListRequestDto(page, perPage);
        ServiceListResDto responseDto = subsidyService.getServiceList(requestDto);
        return ResponseDto.builder()
                .messsage("success")
                .data(responseDto)
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/gov24/v1/serviceDetail")
    public ResponseDto getServiceDetail(String serviceId) {
        ServiceDetailListResDto responseDto = subsidyService.getServiceDetails(serviceId);
        return ResponseDto.builder()
                .messsage("success")
                .data(responseDto)
                .status(HttpStatus.OK)
                .build();
    }
}
