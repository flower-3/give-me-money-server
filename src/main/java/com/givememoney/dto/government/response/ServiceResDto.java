package com.givememoney.dto.government.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ServiceResDto {

    private ServiceResDto(){ }
    @JsonProperty("서비스ID")
    private String id;
    @JsonProperty("지원유형")
    private String supportType;
    @JsonProperty("서비스명")
    private String serviceName;
    @JsonProperty("서비스목적")
    private String servicePurpose;
    @JsonProperty("지원대상")
    private String targetApplicants;
    @JsonProperty("선정기준")
    private String lectotype;
    @JsonProperty("지원내용")
    private String supportDetails;
    @JsonProperty("신청방법")
    private String applyMeans;
    @JsonProperty("신청기한")
    private String deadLine;
    @JsonProperty("상세조회URL")
    private String url;
    @JsonProperty("소관기관코드")
    private String agencyCode;
    @JsonProperty("소관기관명")
    private String agencyName;
    @JsonProperty("부서명")
    private String agencyDepartmentName;
    @JsonProperty("조회수")
    private String hits;
}
