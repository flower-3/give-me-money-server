package com.givememoney.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.givememoney.dto.government.request.GovernmentServiceListRequestDto;
import com.givememoney.dto.government.response.ServiceDetailListResDto;
import com.givememoney.dto.government.response.ServiceListResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GrantService {

    @Value("${oauth.government.decoded_key}")
    private String decodedKey;
    @Value("${oauth.government.service_list_url}")
    private String serviceListUrl;
    @Value("${oauth.government.service_detail_url}")
    private String serviceDetailUrl;
    @Value("${oauth.government.support_conditions_url}")
    private String supportConditionsUrl;

    public ServiceListResDto getServiceList(GovernmentServiceListRequestDto requestDto){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("accept","application/json");
        requestHeaders.add("Content-type", "application/json");
        requestHeaders.add("Authorization", "Infuser "+decodedKey);

        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> requestBodys =
                new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String,String>> requestEntity =
                new HttpEntity<MultiValueMap<String,String>>(requestBodys, requestHeaders);

        ResponseEntity<ServiceListResDto> responseEntity = restTemplate.exchange(
                String.format(serviceListUrl+"?page=%s&perPage=%s",requestDto.getPage(),requestDto.getPerPage()),
                HttpMethod.GET,
                requestEntity,
                ServiceListResDto.class
        );

        return responseEntity.getBody();
    }

    public ServiceDetailListResDto getServiceDetails(String serviceId){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("accept","application/json");
        requestHeaders.add("Authorization", "Infuser "+decodedKey);

        MultiValueMap<String, String> requestBodys = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String,String>> requestEntity =
                new HttpEntity<MultiValueMap<String,String>>(requestBodys, requestHeaders);
        ResponseEntity<ServiceDetailListResDto> responseEntity = restTemplate.exchange(
                String.format(serviceDetailUrl+"?cond[SVC_ID::EQ]=%s",serviceId),
                HttpMethod.GET,
                requestEntity,
                ServiceDetailListResDto.class
        );
        return responseEntity.getBody();
    }

}
