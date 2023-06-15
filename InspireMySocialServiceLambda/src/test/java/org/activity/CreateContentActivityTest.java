package org.activity;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.Converter.ModelConverter;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.model.ContentModel;
import org.openaiservice.OpenAiDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CreateContentActivityTest {

    @Mock
    private ContentDao contentDao;
    @Mock
    private OpenAiDao openAiDao;
    private CreateContentActivity createContentActivity;
    @BeforeEach
    public void setup(){
        initMocks(this);
        createContentActivity = new CreateContentActivity(contentDao,openAiDao, userDao);
    }
    @Test
    void handleRequest_validRequest_results() {
        // GIVEN
        CreateContentRequest createContentRequest = CreateContentRequest.builder()
                .withUserId("user email")
                .withContentType("FB Post")
                .withTone("funny")
                .withAudience("general public")
                .withTopic("What is the funny joke you know?")
                .withWordCount(150)
                .build();

        Usage usage = new Usage();
        usage.setPromptTokens(100L);
        usage.setCompletionTokens(150L);
        usage.setTotalTokens(250L);

        ChatMessage chatMessage = new ChatMessage("assistant",
                "A dog walked into a bar and said beer me please");

        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
        chatCompletionChoice.setIndex(0);
        chatCompletionChoice.setFinishReason("it finished");
        chatCompletionChoice.setMessage(chatMessage);

        List<ChatCompletionChoice> chatCompletionChoiceList = new ArrayList<>();
        chatCompletionChoiceList.add(chatCompletionChoice);

        ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
        chatCompletionResult.setId("Chat1234");
        chatCompletionResult.setObject("Chat completion");
        chatCompletionResult.setCreated(100L);
        chatCompletionResult.setModel("Chat 4");
        chatCompletionResult.setChoices(chatCompletionChoiceList);
        chatCompletionResult.setUsage(usage);

        Content newContent = new Content();
        newContent.setUserID(createContentRequest.getUserId());
        newContent.setContentType(createContentRequest.getContentType());
        newContent.setContentId(chatCompletionResult.getId());
        newContent.setTone(createContentRequest.getTone());
        newContent.setAudience(createContentRequest.getAudience());
        newContent.setTopic(createContentRequest.getTopic());
        newContent.setWordCount(createContentRequest.getWordCount());
        newContent.setDeleted(false);
        newContent.setAiMessage(chatCompletionResult.getChoices().get(0).getMessage().getContent());
        long promptUsage = chatCompletionResult.getUsage().getPromptTokens();
        Integer promptTokensInt = (int) promptUsage;
        newContent.setPromptTokens(promptTokensInt);
        long completionUsage = chatCompletionResult.getUsage().getCompletionTokens();
        Integer completionUsageInt = (int) completionUsage;
        newContent.setCompletionTokens(completionUsageInt);
        newContent.setTotalTokens(completionUsageInt + promptTokensInt);

        when(openAiDao.createContent(createContentRequest)).thenReturn(chatCompletionResult);
        when(contentDao.saveContent(newContent)).thenReturn(newContent);

        ContentModel contentModel = new ModelConverter().toContentModel(newContent);

        CreateContentResult expected = CreateContentResult.builder()
                .withContentModel(contentModel)
                .build();
        // WHEN

        CreateContentResult createContentResult = createContentActivity.handleRequest(createContentRequest);
        System.out.println("*** expected object is :" + expected + " ***");
        System.out.println("*** returned object is :" + createContentResult+ " ***");



        // THEN
        assertEquals(expected, createContentResult);
    }
}