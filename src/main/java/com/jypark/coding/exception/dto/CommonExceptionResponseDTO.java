package com.jypark.coding.exception.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonExceptionResponseDTO {

    private LocalDateTime timestamp;
    private int code;
    private HttpStatus status;
    private String message;
    private String path;

    public CommonExceptionResponseDTO(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.path = path;
    }

}
