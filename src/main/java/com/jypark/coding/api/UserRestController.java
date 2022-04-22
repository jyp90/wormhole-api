package com.jypark.coding.api;

import com.jypark.coding.domain.users.dto.UserCommonResponseDTO;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.dto.UserGetDTO;
import com.jypark.coding.domain.users.service.UserService;
import com.jypark.coding.exception.UserParameterInvalidException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public UserCommonResponseDTO create(@RequestBody UserCreateRequestDTO request) {
        if(request.isInvalid()) {
            throw UserParameterInvalidException.create(request);
        }
        return userService.create(request);
    }
}
