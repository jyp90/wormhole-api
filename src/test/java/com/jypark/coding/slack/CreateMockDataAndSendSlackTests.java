package com.jypark.coding.slack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jypark.coding.common.slack.service.SlackService;
import com.jypark.coding.domain.alerts.dto.AlertDTO;
import com.jypark.coding.domain.alerts.dto.AlertDTO.Request;
import com.jypark.coding.domain.alerts.enumerate.ServerSeverity;
import com.jypark.coding.domain.usergroups.dto.UserConnectRequestDTO;
import com.jypark.coding.domain.usergroups.service.UserGroupService;
import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import com.jypark.coding.domain.users.service.UserService;
import com.jypark.coding.exception.BadRequestException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CreateMockDataAndSendSlackTests {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private SlackService slackService;

    private final static String MENTION = "@";
    private final static String USERGROUP_MENTION = "@@";

    List<String> userGroupList = List.of("kakao-webhook-2", "kakao-webhook-3", "dummy-1", "dummy-2");
    List<String> userNicknameList = List.of("jypark", "chulsu", "ryan", "muzi", "frodo",
        "user1", "user2", "user3", "user4", "user5", "user6");

    @Test
    @DisplayName("사용자 Mock data 입력")
    void createMockUsers() {
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().substring(0, 10);
            String createdNickname = uuid + i;
            UserCreateRequestDTO createRequestDTO = new UserCreateRequestDTO();
            createRequestDTO.setUsername(createdNickname);
            createRequestDTO.setNickname(createdNickname);
            createRequestDTO.setMemberId(createdNickname);
            userService.create(createRequestDTO);

            UserConnectRequestDTO joinGroup1 = new UserConnectRequestDTO();
            joinGroup1.setNickname(createdNickname);
            joinGroup1.setGroupname(userGroupList.get(i % 4));
            userGroupService.joinGroup(joinGroup1);
        }
    }

    @Test
    @DisplayName("사용자 및 사용자 그룹 생성 테스트")
    void createUserAndUserGroupTest() {
        try {
            AtomicInteger createdUserGroupCount = new AtomicInteger(0);

            userGroupList.forEach(userGroupName -> {
                createdUserGroupCount.incrementAndGet();
                userGroupService.createGroup(userGroupName);
            });

            for (String nickname : userNicknameList) {
                UserCreateRequestDTO createRequestDTO = new UserCreateRequestDTO();
                createRequestDTO.setUsername(nickname);
                createRequestDTO.setNickname(nickname);
                userService.create(createRequestDTO);
            }

            for (String nickname : userNicknameList) {
                UserConnectRequestDTO joinGroupTest = new UserConnectRequestDTO();
                joinGroupTest.setNickname(nickname);
                joinGroupTest.setGroupname(userGroupList.get(0));
                userGroupService.joinGroup(joinGroupTest);
            }
            {
                UserConnectRequestDTO joinGroup1 = new UserConnectRequestDTO();
                joinGroup1.setNickname(userNicknameList.get(0));
                joinGroup1.setGroupname(userGroupList.get(1));
                userGroupService.joinGroup(joinGroup1);
            }
            {
                UserConnectRequestDTO joinGroup1 = new UserConnectRequestDTO();
                joinGroup1.setNickname(userNicknameList.get(1));
                joinGroup1.setGroupname(userGroupList.get(1));
                userGroupService.joinGroup(joinGroup1);
            }
            {
                UserConnectRequestDTO joinGroup1 = new UserConnectRequestDTO();
                joinGroup1.setNickname(userNicknameList.get(2));
                joinGroup1.setGroupname(userGroupList.get(1));
                userGroupService.joinGroup(joinGroup1);
            }
            {
                UserConnectRequestDTO joinGroup1 = new UserConnectRequestDTO();
                joinGroup1.setNickname(userNicknameList.get(3));
                joinGroup1.setGroupname(userGroupList.get(1));
                userGroupService.joinGroup(joinGroup1);
            }
            assertEquals(createdUserGroupCount.get(), 4);
        } catch (BadRequestException badRequestException) {
        }
    }

    @Test
    @DisplayName("알림 특정 사용자 그룹 발송 테스트")
    void sendAlertToUserGroup() {
        createUserAndUserGroupTest();
        AlertDTO.Request request = new Request();
        request.getTarget().add(USERGROUP_MENTION + "kakao-webhook-1");
        final SlackService send = initAndSendTo(request);
        assertEquals(send.isSuccess(), true);
    }

    @Test
    @DisplayName("알림 사용자 그룹과 사용자 발송 후 발송카운트 출력 테스트")
    void sendAlertToUserGroupsAndUsersAndPrintSendCount() {
        createUserAndUserGroupTest();
        AlertDTO.Request request1 = new Request();
        request1.getTarget().add(USERGROUP_MENTION + userGroupList.get(0));
        request1.getTarget().add(MENTION + userNicknameList.get(0));
        request1.getTarget().add(MENTION + userNicknameList.get(1));
        request1.getTarget().add(MENTION + userNicknameList.get(2));
        request1.getTarget().add(MENTION + userNicknameList.get(3));
        final SlackService send = initAndSendTo(request1);

        // 2번째 알림 발송
        AlertDTO.Request request2 = new Request();
        request2.getTarget().add(USERGROUP_MENTION + userGroupList.get(1));
        request2.getTarget().add(MENTION + userNicknameList.get(1));
        final SlackService send2 = initAndSendTo(request2);

        assertEquals(send.isSuccess(), false);
        assertEquals(send.getReceivedUserCount(), 4);
        assertEquals(send2.isSuccess(), false);
        assertEquals(send2.getReceivedUserCount(), 4);
    }

    @Test
    @DisplayName("알림 전체 발송 테스트")
    void sendAlertToAll()  {
        try {
            createUserAndUserGroupTest();
            AlertDTO.Request request = new Request();
            request.getTarget().add("@all");
            final SlackService send = initAndSendTo(request);
            assertEquals(send.isSuccess(), false);
        } catch (BadRequestException badRequestException) {}
    }

    private SlackService initAndSendTo(AlertDTO.Request request) {
        request.setSeverity(ServerSeverity.normal);
        request.setMessage("target to = " + request.getTarget());
        return slackService.to(request).send();
    }
}
