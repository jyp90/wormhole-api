package com.jypark.coding.domain.users.dto;

import com.jypark.coding.domain.users.entity.User;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class UserGetDTO {

    private HttpStatus status;
    private String message;
    private User user;

    @Data
    public static class Response {

        private Long id;
        private String username;
        private String nickname;
        private boolean deleted;

        public Response(User user) {
            if (Objects.nonNull(user)) {
                this.id = user.getUserId();
                this.deleted = user.isDeleted();
            }
        }
    }

    public static UserGetDTO of(User user) {
        return UserGetDTO.builder()
            .user(user)
            .status(HttpStatus.OK)
            .build();
    }

}
