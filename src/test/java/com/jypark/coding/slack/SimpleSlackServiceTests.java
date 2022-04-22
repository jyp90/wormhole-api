package com.jypark.coding.slack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jypark.coding.common.slack.service.SlackService;
import com.jypark.coding.domain.alerts.dto.AlertDTO;
import com.jypark.coding.domain.alerts.dto.AlertDTO.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SimpleSlackServiceTests {

    @Autowired
    private SlackService slackService;

    @Test
    @DisplayName("Slack 메시지 전체 전송 테스트")
    void sendSlackMessageToAll() {
        AlertDTO.Request request = new Request();
        request.getTarget().add("@all");
        final SlackService send = slackService.to(request).send();
        System.out.println(send.isSuccess());
    }
}
