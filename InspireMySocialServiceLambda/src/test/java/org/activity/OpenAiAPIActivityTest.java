package org.activity;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.metrics.MetricsPublisher;
import org.mockito.Mock;
import org.openaiservice.OpenAiDao;
import org.openaiservice.OpenAiService;
import org.openaiservice.SecretHolder;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class OpenAiAPIActivityTest {
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
//    public void getContent_valid_success(){
//
//        OpenAiService openAiService = new OpenAiService(SecretHolder.getOpenAiApiKey());
//        OpenAiDao openAiDao = new OpenAiDao(openAiService,null );
//        CreateContentRequest createContentRequest = new CreateContentRequest("Jeff", "Face Book Post",
//                "condescending", "software engineers", "hire me please, I'm cool", 200 );
//        ChatCompletionResult result = openAiDao.createContent(createContentRequest);
//        System.out.println("-".repeat(140));
//        System.out.println("ContentID: " + result.getId());
//        System.out.println("Model Used: " + result.getModel());
//        System.out.println("Object: " + result.getObject());
//        System.out.println("Created: " + result.getCreated());
//        //System.out.println("Prompt Tokens: " + result.getUsage().getPromptTokens());
//        //System.out.println("Completion Tokens: " + result.getUsage().getCompletionTokens());
//        //System.out.println("Total Tokens: " + result.getUsage().getTotalTokens());
//        System.out.println("Message: " + result.getChoices().get(0).getMessage());
//            }
//
//     @Test
//     public void getContent_valid_success(){
//        // GIVEN
//         CreateContentRequest createContentRequest = CreateContentRequest.builder()
//                 .withUserId("user1234")
//                 .withConentType("fbPost")
//                 .withTone("friendly")
//                 .withAudience("general public")
//                 .withTopic("Tell me something cool")
//                 .withWordCount(250)
//                 .build();
//
//        String expected = "Ice is cold";
//        when(openAiDao.createContent(createContentRequest)).thenReturn(expected);
//
//        //WHEN
//        String result = openAiDao.createContent(createContentRequest);
//
//        //THEN
//         assertEquals(expected, result);
//
//     }

}