package com.jypark.coding.exception.dto;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

@Getter
public class ServerExceptionResponseDTO extends CommonExceptionResponseDTO {

    private String cause;

    public ServerExceptionResponseDTO(HttpStatus status, String message, String path, Throwable cause) {
        super(status, message, path);
        this.cause = ExceptionUtils.getStackTrace(cause);
    }
}
