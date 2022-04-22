package com.jypark.coding.usergroups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.jypark.coding.domain.usergroups.dto.UserConnectRequestDTO;
import com.jypark.coding.domain.usergroups.dto.UserGroupCommonResponseDTO;
import com.jypark.coding.domain.usergroups.service.UserGroupService;
import com.jypark.coding.domain.users.dto.UserCommonResponseDTO;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.service.UserService;
import com.jypark.coding.exception.BadRequestException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserGroupServiceTests {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자 그룹 생성 테스트")
    void createUserGroupTest() {
        String groupname = UUID.randomUUID().toString();
        final UserGroupCommonResponseDTO createDTO = userGroupService.createGroup(groupname);
        assertEquals(createDTO.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("사용자 그룹 중복생성 방지 테스트")
    void createUserGroupDuplicateTest() {
        try {
            String groupname = UUID.randomUUID() + "1";
            final UserGroupCommonResponseDTO createDTO = userGroupService.createGroup(groupname);
            final UserGroupCommonResponseDTO createDTO2 = userGroupService.createGroup(groupname);
            assertNotEquals(createDTO.getStatus(), createDTO2.getStatus());
            assertEquals(createDTO.getStatus(), HttpStatus.CREATED);
            assertEquals(createDTO2.getStatus(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException badRequestException) {}
    }

    @Test
    @DisplayName("사용자 그룹 가입 테스트")
    void joinUserToUserGroupTest() {
        String groupname = UUID.randomUUID() + "2";
        String userNickname = UUID.randomUUID() + "3";
        UserCreateRequestDTO userDTO = new UserCreateRequestDTO();
        userDTO.setUsername("박준용");
        userDTO.setNickname(userNickname);
        userGroupService.createGroup(groupname);
        userService.create(userDTO);

        UserConnectRequestDTO userConnectRequestDTO = new UserConnectRequestDTO();
        userConnectRequestDTO.setGroupname(groupname);
        userConnectRequestDTO.setNickname(userNickname);
        final UserGroupCommonResponseDTO userGroupCommonResponseDTO = userGroupService.joinGroup(userConnectRequestDTO);

        assertEquals(userGroupCommonResponseDTO.getStatus(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("같은 사용자 그룹 가입 중복 방지 테스트")
    void protectSameUserMultipleJoinToUserGroupTest() {
        try {
            String groupname = UUID.randomUUID() + "4";
            String userNickname = UUID.randomUUID() + "5";
            UserCreateRequestDTO userDTO = new UserCreateRequestDTO();
            userDTO.setUsername("박준용");
            userDTO.setNickname(userNickname);
            userGroupService.createGroup(groupname);
            userService.create(userDTO);
            UserConnectRequestDTO userConnectRequestDTO = new UserConnectRequestDTO();
            userConnectRequestDTO.setGroupname(groupname);
            userConnectRequestDTO.setNickname(userNickname);
            final UserGroupCommonResponseDTO userGroupCommonResponseDTO = userGroupService.joinGroup(userConnectRequestDTO);
            final UserGroupCommonResponseDTO duplicateUserGroupCommonResponseDTO = userGroupService.joinGroup(userConnectRequestDTO);

            assertEquals(userGroupCommonResponseDTO.getStatus(), HttpStatus.CREATED);
            assertEquals(duplicateUserGroupCommonResponseDTO.getStatus(), HttpStatus.NO_CONTENT);
        } catch (BadRequestException badRequestException) {

        }
    }

    @Test
    @DisplayName("같은 사용자 그룹 가입 중복 방지 테스트")
    void leaveUserFromUserGroupTest() {
        String groupname = UUID.randomUUID() + "6";
        String userNickname = UUID.randomUUID() + "7";
        UserCreateRequestDTO userDTO = new UserCreateRequestDTO();
        userDTO.setUsername("박준용");
        userDTO.setNickname(userNickname);
        userGroupService.createGroup(groupname);
        final UserCommonResponseDTO userCommonResponseDTO = userService.create(userDTO);
        userCommonResponseDTO.getUser().getId();

        UserConnectRequestDTO userConnectRequestDTO = new UserConnectRequestDTO();
        userConnectRequestDTO.setGroupname(groupname);
        userConnectRequestDTO.setNickname(userNickname);

        final UserGroupCommonResponseDTO joinGroupDTO = userGroupService.joinGroup(userConnectRequestDTO);
        assertEquals(joinGroupDTO.getStatus(), HttpStatus.CREATED);

        final UserGroupCommonResponseDTO leaveGroupDTO = userGroupService.leaveGroup(userConnectRequestDTO);
        assertEquals(leaveGroupDTO.getStatus(), HttpStatus.OK);
    }
}
