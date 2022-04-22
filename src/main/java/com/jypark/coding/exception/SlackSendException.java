package com.jypark.coding.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
@ResponseStatus
public class SlackSendException extends RuntimeException {
    public SlackSendException(String message, Exception exception) {
        super(message, exception);
    }

    public static SlackSendException create(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return new SlackSendException("Slack 알림 발송 중 에러가 발생하였습니다.", e);
    }
}
