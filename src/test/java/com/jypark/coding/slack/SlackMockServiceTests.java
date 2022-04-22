package com.jypark.coding.slack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jypark.coding.common.slack.properties.SlackProperties;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SlackMockServiceTests {

    @Autowired
    private SlackProperties slackProperties;

    @Test
    @DisplayName("Slack 메시지 API 사용하여 테스트")
    void sendSlackUsingMock() throws SlackApiException, IOException {
        final Slack slack = Slack.getInstance();
        final MethodsClient methods = slack.methods(slackProperties.getToken());

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
            .channel("#kakao-webhook")
            .text("Test Message")
            .build();

        methods.chatPostMessage(request);
    }

    @Test
    @DisplayName("Slack 메시지 API V2 테스트")
    void sendSlackUsingMockV2() throws SlackApiException, IOException {
        final Slack slack = Slack.getInstance();
        final ChatPostMessageResponse response = slack.methods(slackProperties.getToken())
            .chatPostMessage(req -> req.channel("U01U7L3HSET").text("Test2"));
        final boolean successToSend = response.isOk();
        assertEquals(successToSend, true);
    }
}
