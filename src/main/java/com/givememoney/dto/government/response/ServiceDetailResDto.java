package com.givememoney.dto.government.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ServiceDetailResDto {
    private ServiceDetailResDto(){}

    @JsonProperty("SVC_ID")
    private String svcId;
    @JsonProperty("지원유형")
    private String supportType;
    @JsonProperty("서비스명")
    private String serviceName;
    @JsonProperty("서비스목적")
    private String servicePurpose;
    @JsonProperty("신청기한")
    private String deadLine;
    @JsonProperty("지원대상")
    private String targetApplicants;
    @JsonProperty("선정기준")
    private String lectotype;
    @JsonProperty("지원내용")
    private String supportDetails;
    @JsonProperty("신청방법")
    private String applyMeans;
    @JsonProperty("구비서류")
    private String needDocuments;
    @JsonProperty("접수기관명")
    private String applyAgencyName;
    @JsonProperty("문의처전화번호")
    private String contactNumber;
    @JsonProperty("온라인신청사이트URL")
    private String applyUrl;
    @JsonProperty("수정일시")
    private String modificationDate;
    @JsonProperty("소관기관명")
    private String agencyName;
    @JsonProperty("행정규칙")
    private String administrationRule;
    @JsonProperty("자치법규")
    private String selfLaws;
    @JsonProperty("법령")
    private String laws;


}
