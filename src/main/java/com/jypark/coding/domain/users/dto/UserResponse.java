package com.jypark.coding.domain.users.dto;

import com.jypark.coding.domain.users.entity.User;
import java.util.Optional;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class UserResponse {

    private boolean found;
    private boolean deleted;
    private HttpStatus status;
    private String message;
    private User user;

    public static UserResponse of(Optional<User> user) {
        return new UserResponse(user);
    }

    public UserResponse(HttpStatus status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }

    public UserResponse(Optional<User> user) {
        final boolean present = user.isPresent();
        this.found = present;
        this.user = present ? user.get() : null;
        this.deleted = present ? user.get().isDeleted() : false;
        setStatus();
    }


    public boolean isAbsent() {
        return !found;
    }

    public User getUser() {
        return isFound() ? this.user : null;
    }

    private void setStatus() {
        if (this.deleted) {
            this.status = HttpStatus.NOT_FOUND;
        }
        if (this.found) {
            this.status = HttpStatus.OK;
        }
        if (isAbsent()) {
            this.status = HttpStatus.BAD_REQUEST;
        }
    }
}
