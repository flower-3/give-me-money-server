package com.givememoney.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.givememoney.dto.government.request.GovernmentServiceListRequestDto;
import com.givememoney.dto.government.response.ServiceDetailListResDto;
import com.givememoney.dto.government.response.ServiceListResDto;
import com.givememoney.util.MultiValueMapConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
class SubsidyServiceTest {

    @Value("${oauth.government.decoded_key}")
    private String decodedKey;
    @Value("${oauth.government.service_list_url}")
    private String serviceListUrl;
    @Value("${oauth.government.service_detail_url}")
    private String serviceDetailUrl;
    GovernmentServiceListRequestDto requestDto;
    private String serviceId;
    @BeforeEach
    void setUp() {
        requestDto = new GovernmentServiceListRequestDto(1,10);
        serviceId = "000000465790";
    }

    @DisplayName("서비스 리스트 페이징 및 파싱 체크")
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

        ResponseEntity<ServiceListResDto> responseEntity = restTemplate.exchange(
                serviceListUrl,
                HttpMethod.GET,
                requestEntity,
                ServiceListResDto.class
        );

        assert responseEntity.getBody().getData().size() == 10;
    }

    @DisplayName("서비스 세부내역 조회 정합성 및 dto값 세팅 확인")
    @Test
    void getServiceDetails() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("accept","application/json");
        requestHeaders.add("Authorization", "Infuser "+decodedKey);

        MultiValueMap<String, String> requestBodys = new LinkedMultiValueMap<>();
        requestBodys.add("cond", serviceId);
        HttpEntity<MultiValueMap<String,String>> requestEntity =
                new HttpEntity<MultiValueMap<String,String>>(requestBodys, requestHeaders);
        ResponseEntity<ServiceDetailListResDto> responseEntity = restTemplate.exchange(
                serviceDetailUrl,
                HttpMethod.GET,
                requestEntity,
                ServiceDetailListResDto.class
        );
        ServiceDetailListResDto serviceDetailListResDto = responseEntity.getBody();
        containRequestService(serviceId, serviceDetailListResDto);
        assert serviceDetailListResDto.getHasRequestValue();
        assert serviceDetailListResDto.getData().get(0).getContactNumber().equals("129");
    }

    private void containRequestService(String serviceId, ServiceDetailListResDto resDto){
        if(resDto.getData().size()==0){
            resDto.setHasRequestValue(false);
            return;
        }
        boolean hasRequestServiceId = serviceId.equals(resDto.getData().get(0).getSvcId()) == true;
        resDto.setHasRequestValue(hasRequestServiceId);
    }
}