package com.jypark.coding.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jypark.coding.domain.users.dto.UserCommonResponseDTO;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.dto.UserGetDTO;
import com.jypark.coding.domain.users.service.UserService;
import com.jypark.coding.exception.DataNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자 조회 테스트")
    void findUserByUserIdTest() {
        final UserGetDTO user = userService.getByUserId(1L);
        assertEquals(user.getStatus(), HttpStatus.OK);
    }

    @Test
    @DisplayName("사용자 입력 테스트")
    void insertUserTest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO();
        requestDTO.setUsername("박준용");
        requestDTO.setNickname(UUID.randomUUID() + "1");
        requestDTO.setMemberId("U01U7L3HSET");

        final UserCommonResponseDTO userCommonResponseDTO = userService.create(requestDTO);
        assertEquals(userCommonResponseDTO.getStatus(), HttpStatus.CREATED);
    }


    @Test
    @DisplayName("사용자 중복 입력 테스트")
    void insertDuplicatedUserTest() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO();
        String nickname = UUID.randomUUID() + "2";
        requestDTO.setUsername("박준용");
        requestDTO.setNickname(nickname);
        final UserCommonResponseDTO userCommonResponseDTO = userService.create(requestDTO);

        UserCreateRequestDTO requestDTO2 = new UserCreateRequestDTO();
        requestDTO.setUsername("박준용");
        requestDTO.setNickname(nickname);
        final UserCommonResponseDTO userCommonResponseDTO1 = userService.create(requestDTO2);

        assertEquals(userCommonResponseDTO.getStatus(), HttpStatus.CREATED);
        assertEquals(userCommonResponseDTO1.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("사용자 탈퇴 테스트")
    void deleteUser() {
        try {
            Long userId = 1L;
            userService.delete(userId);
            final UserGetDTO get = userService.getByUserId(userId);
            assertEquals(get.getStatus(), HttpStatus.NOT_FOUND);
        } catch (DataNotFoundException dataNotFoundException) {}
    }
}
