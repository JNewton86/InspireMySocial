package org.openaiservice;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.activity.request.CreateContentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.metrics.MetricsPublisher;
import org.mockito.Mock;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class OpenAiDaoTest {
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private OpenAiService openAiService;

    private OpenAiDao openAiDao;
    @BeforeEach
    public void setup(){
        initMocks(this);
        openAiDao = new OpenAiDao(openAiService,metricsPublisher);
    }

//    @Test
//    public void getContent_validRequest_success(){
//        // GIVEN
//        CreateContentRequest createContentRequest = CreateContentRequest.builder()
//                .withUserId("user1234")
//                .withContentType("fbPost")
//                .withTone("friendly")
//                .withAudience("general public")
//                .withTopic("Tell me something cool")
//                .withWordCount(250)
//                .build();
//
//        ChatCompletionResult expected = new ChatCompletionResult();
//        expected.setId("1234");
//        expected.setModel("Chat4");
//        expected.setObject("Text-Completion");
//        expected.setCreated(150L);
//        Usage usage = new Usage();
//        usage.setPromptTokens(150L);
//        usage.setCompletionTokens(500L);
//        usage.setTotalTokens(650L);
//        expected.setUsage(usage);
//        List<ChatCompletionChoice> choices = new ArrayList<>();
//        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
//        chatCompletionChoice.setIndex(0);
//        chatCompletionChoice.setFinishReason("finished");
//        ChatMessage chatMessage = new ChatMessage("assistant", "This worked!");
//        chatCompletionChoice.setMessage(chatMessage);
//        expected.setChoices(choices);
//        when(openAiDao.createContent(createContentRequest)).thenReturn(expected);
//
//        //WHEN
//        ChatCompletionResult result = openAiDao.createContent(createContentRequest);
//
//        //THEN
//        assertEquals(expected, result);
//    }

}