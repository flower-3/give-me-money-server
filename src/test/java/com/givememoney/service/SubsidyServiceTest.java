package com.givememoney.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.givememoney.dto.government.request.GovernmentServiceListRequestDto;
import com.givememoney.dto.government.response.GovernmentServiceListResponseDto;
import com.givememoney.util.MultiValueMapConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class SubsidyServiceTest {

    @Value("${oauth.government.decoded_key}")
    private String decodedKey;
    @Value("${oauth.government.service_list_url}")
    private String serviceListUrl;
    GovernmentServiceListRequestDto requestDto;
    @BeforeEach
    void setUp() {
        requestDto = new GovernmentServiceListRequestDto(1,10);
    }

    @Test
    void getServiceList() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("accept","application/json");
        requestHeaders.add("Content-type", "application/json");
        requestHeaders.add("Authorization", "Infuser "+decodedKey);

        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> requestBodys =
                MultiValueMapConverter.convert(objectMapper, requestDto);

        HttpEntity<MultiValueMap<String,String>> requestEntity =
                new HttpEntity<MultiValueMap<String,String>>(requestBodys, requestHeaders);

        ResponseEntity<GovernmentServiceListResponseDto> responseEntity = restTemplate.exchange(
                serviceListUrl,
                HttpMethod.GET,
                requestEntity,
                GovernmentServiceListResponseDto.class
        );

        assert responseEntity.getBody().getData().size() == 10;
    }
}