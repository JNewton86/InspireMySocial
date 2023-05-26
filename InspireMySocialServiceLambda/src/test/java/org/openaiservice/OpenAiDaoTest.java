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

    //TODO Integration Test that makes live call to OpenAi Disabled for time constraints when building
//    @Test
//    public void getContent_valid_success(){

//        OpenAiService openAiService = new OpenAiService(SecretHolder.getOpenAiApiKey(), Duration.ofSeconds(240));
//        OpenAiDao openAiDao = new OpenAiDao(openAiService,null );
//        CreateContentRequest createContentRequest = new CreateContentRequest("Jeff", "Face Book Post",
//                "condescending", "software engineers", "hire me please, I'm cool", 200 );
//        ChatCompletionResult result = openAiDao.createContent(createContentRequest);
//        System.out.println("-".repeat(140));
//        System.out.println("ContentID: " + result.getId());
//        System.out.println("Model Used: " + result.getModel());
//        System.out.println("Object: " + result.getObject());
//        System.out.println("Created: " + result.getCreated());
//        System.out.println("Prompt Tokens: " + result.getUsage().getPromptTokens());
//        System.out.println("Completion Tokens: " + result.getUsage().getCompletionTokens());
//        System.out.println("Total Tokens: " + result.getUsage().getTotalTokens());
//        System.out.println("Message: " + result.getChoices().get(0).getMessage().getContent());
//            }

    @Test
    public void getContent_validRequest_success(){
        // GIVEN
        CreateContentRequest createContentRequest = CreateContentRequest.builder()
                .withUserId("user1234")
                .withContentType("fbPost")
                .withTone("friendly")
                .withAudience("general public")
                .withTopic("Tell me something cool")
                .withWordCount(250)
                .build();

        ChatCompletionResult expected = new ChatCompletionResult();
        expected.setId("1234");
        expected.setModel("Chat4");
        expected.setObject("Text-Completion");
        expected.setCreated(150L);
        Usage usage = new Usage();
        usage.setPromptTokens(150L);
        usage.setCompletionTokens(500L);
        usage.setTotalTokens(650L);
        expected.setUsage(usage);
        List<ChatCompletionChoice> choices = new ArrayList<>();
        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setIndex(0);
        chatCompletionChoice.setFinishReason("finished");
        ChatMessage chatMessage = new ChatMessage("assistant", "This worked!");
        chatCompletionChoice.setMessage(chatMessage);
        expected.setChoices(choices);
        when(openAiDao.createContent(createContentRequest)).thenReturn(expected);

        //WHEN
        ChatCompletionResult result = openAiDao.createContent(createContentRequest);

        //THEN
        assertEquals(expected, result);
    }

}