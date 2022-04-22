package com.jypark.coding.domain.users.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String id;
    private String password;

    public boolean isIdNull() {
        return StringUtils.isBlank(id);
    }

    public boolean isPasswordNull() {
        return StringUtils.isBlank(password);
    }
}
