package com.jypark.coding.domain.users.service;

import com.jypark.coding.domain.users.dto.UserCommonResponseDTO;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.dto.UserGetDTO;
import com.jypark.coding.domain.users.dto.UserLoginRequestDTO;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserGetDTO getForLogin(UserLoginRequestDTO requestDTO);
    void logout(HttpServletRequest httpServletRequest);
    UserCommonResponseDTO create(UserCreateRequestDTO request);
}
