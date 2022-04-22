package com.jypark.coding.domain.users.service;

import com.jypark.coding.common.config.SecurityPasswordEncoder;
import com.jypark.coding.domain.users.dto.UserCommonResponseDTO;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.dto.UserGetDTO;
import com.jypark.coding.domain.users.dto.UserLoginRequestDTO;
import com.jypark.coding.domain.users.entity.User;
import com.jypark.coding.domain.users.repository.UserRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final SecurityPasswordEncoder passwordEncoder;

    @Override
    public UserGetDTO getForLogin(UserLoginRequestDTO requestDTO) {
        final Optional<User> optionalUser = userRepository.findByUsername(requestDTO.getId());
        if(optionalUser.isEmpty()) {
            return UserGetDTO.builder()
                .status(HttpStatus.NOT_FOUND)
                .build();
        }
        final User user = optionalUser.get();
        if(isMatchPassword(user, requestDTO.getPassword())) {
            return UserGetDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .build();
        }

        return UserGetDTO.of(user);
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest) {
        final HttpSession session = httpServletRequest.getSession();
        session.invalidate();
    }

    @Override
    public UserCommonResponseDTO create(UserCreateRequestDTO request) {
        if(request.isInvalid()) {
            return UserCommonResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .build();
        }
        final User savedUser = userRepository.save(new User(request));
        return UserCommonResponseDTO.ok("", savedUser);
    }

    private boolean isMatchPassword(User user, String anotherPassword) {
        return StringUtils.equals(passwordEncoder.encoder().encode(anotherPassword), user.getPassword());
    }
}
