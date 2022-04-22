package com.jypark.coding.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Exception e) {
        super(message, e);
        log.error(ExceptionUtils.getStackTrace(e));
    }

    public static InternalServerException of(Exception e) {
        return new InternalServerException("서버에 에러가 발생하였습니다.", e);
    }

    public static InternalServerException of(String message) {
        return new InternalServerException(message);
    }
}
