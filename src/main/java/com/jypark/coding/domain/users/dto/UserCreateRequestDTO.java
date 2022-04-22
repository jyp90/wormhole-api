package com.jypark.coding.domain.users.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class UserCreateRequestDTO {

    private String username;
    private String password;
    private String email;

    public boolean isInvalid() {
        return StringUtils.isBlank(username) || StringUtils.isBlank(password)
            || StringUtils.isBlank(email);
    }
}
