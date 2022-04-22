package com.jypark.coding.domain.users.dto;

import com.jypark.coding.domain.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
public class UserCommonResponseDTO {

    private HttpStatus status;
    private String message;
    private User user;

    public static UserCommonResponseDTO ok(String message, User user) {
        return new UserCommonResponseDTO(HttpStatus.OK,
            message,
            user);
    }
}
