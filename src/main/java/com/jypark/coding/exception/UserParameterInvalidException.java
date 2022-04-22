package com.jypark.coding.exception;

import static com.jypark.coding.exception.constants.ExceptionConstants.PARAMETER_REQUIRED_SUFFIX;

import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserParameterInvalidException extends RuntimeException {
    public UserParameterInvalidException(String message) {
        super(message);
    }

    public static UserParameterInvalidException create() {
        return new UserParameterInvalidException("userId " + PARAMETER_REQUIRED_SUFFIX);
    }

    public static UserParameterInvalidException create(UserCreateRequestDTO request) {
        if (StringUtils.isBlank(request.getEmail())) {
            return new UserParameterInvalidException("email " + PARAMETER_REQUIRED_SUFFIX);
        }
        if (StringUtils.isBlank(request.getUsername())) {
            return new UserParameterInvalidException("id " + PARAMETER_REQUIRED_SUFFIX);
        }

        return null;
    }
}
