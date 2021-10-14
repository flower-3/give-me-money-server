package com.givememoney.dto.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private HttpStatus status;
    private String messsage;
    private T data;

}
