package org.activity;

import com.theokanning.openai.Usage;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.dynamodb.ContentDao;
import org.dynamodb.UserDao;
import org.dynamodb.models.Content;
import org.dynamodb.models.User;
import org.exception.InsufficientCreditsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.model.ContentModel;
import org.openaiservice.OpenAiDao;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateContentActivityTest {

    private ContentDao contentDao;
    private OpenAiDao openAiDao;
    private UserDao userDao;
    private CreateContentActivity createContentActivity;

    @BeforeEach
    void setUp() {
        contentDao = Mockito.mock(ContentDao.class);
        openAiDao = Mockito.mock(OpenAiDao.class);
        userDao = Mockito.mock(UserDao.class);
        createContentActivity = new CreateContentActivity(contentDao, openAiDao, userDao);
    }

    @Test
    void handleRequest_throwsException_whenUserHasInsufficientCredits() {
        CreateContentRequest request = new CreateContentRequest("userId", "contentType", "topic", "tone", "audience", 100);
        User user = new User();
        user.setCreditBalance(0);

        Mockito.when(userDao.getUser(request.getUserId())).thenReturn(user);

        assertThrows(InsufficientCreditsException.class, () -> createContentActivity.handleRequest(request));
    }

    @Test
    void handleRequest_callsOpenAiDao_withCreateContentRequest() {
        CreateContentRequest request = new CreateContentRequest("userId", "contentType", "topic", "tone", "audience", 100);
        User user = new User();
        user.setCreditBalance(100);
        ChatCompletionResult completionResult = createSampleChatCompletionResult();

        Mockito.when(userDao.getUser(request.getUserId())).thenReturn(user);
        Mockito.when(openAiDao.createContent(request)).thenReturn(completionResult);

        createContentActivity.handleRequest(request);

        Mockito.verify(openAiDao).createContent(request);
    }

    @Test
    void handleRequest_savesContent_toDatabase() {
        CreateContentRequest request = new CreateContentRequest("userId", "contentType", "topic", "tone", "audience", 100);
        User user = new User();
        user.setCreditBalance(100);
        ChatCompletionResult completionResult = createSampleChatCompletionResult();
        Content content = new Content();

        Mockito.when(userDao.getUser(request.getUserId())).thenReturn(user);
        Mockito.when(openAiDao.createContent(request)).thenReturn(completionResult);
        Mockito.when(contentDao.saveContent(Mockito.any(Content.class))).thenReturn(content);

        createContentActivity.handleRequest(request);

        Mockito.verify(contentDao).saveContent(Mockito.any(Content.class));
    }

    @Test
    void handleRequest_returnsCreateContentResult_withCreatedContentModel() {
        CreateContentRequest request = new CreateContentRequest("userId", "contentType", "topic", "tone", "audience", 100);
        User user = new User();
        user.setCreditBalance(100);
        ChatCompletionResult completionResult = createSampleChatCompletionResult();
        Content content = createSampleContent();

        Mockito.when(userDao.getUser(request.getUserId())).thenReturn(user);
        Mockito.when(openAiDao.createContent(request)).thenReturn(completionResult);
        Mockito.when(contentDao.saveContent(Mockito.any(Content.class))).thenReturn(content);

        CreateContentResult result = createContentActivity.handleRequest(request);

        ContentModel contentModel = result.getContentModel();
        assertEquals(content.getContentId(), contentModel.getContentId());
        assertEquals(content.getAiMessage(), contentModel.getAiMessage());
        // ... other assertions for the remaining content fields.
    }

    private ChatCompletionResult createSampleChatCompletionResult() {
       Usage usage = new Usage();
       usage.setTotalTokens(100);
       usage.setCompletionTokens(50);
       usage.setPromptTokens(50);

       ChatMessage chatMessage = new ChatMessage("User","I really really enjoy writing tests");
       ChatMessage chatMessage2 = new ChatMessage("Assistant", "Here's a sarcastic FB Post about how " +
               "much fun writing unit tests are");

       ChatCompletionChoice choice = new ChatCompletionChoice();
       choice.setMessage(chatMessage);
       choice.setIndex(0);
       choice.setFinishReason("finished");

        ChatCompletionChoice choice2 = new ChatCompletionChoice();
        choice.setMessage(chatMessage2);
        choice.setIndex(1);
        choice.setFinishReason("finished");

       List<ChatCompletionChoice> choices = new ArrayList<>();
       choices.add(choice);
        choices.add(choice2);

       ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
       chatCompletionResult.setUsage(usage);
       chatCompletionResult.setCreated(10l);

       chatCompletionResult.setChoices(choices);
       chatCompletionResult.setModel("gpt4");
       chatCompletionResult.setId("test1234");
       chatCompletionResult.setObject("object");
        return chatCompletionResult;
    }

    private Content createSampleContent() {
      Content sampleContent = new Content();
      sampleContent.setContentId("test1234");
      sampleContent.setDeleted(false);
      sampleContent.setContentType("FB");
      sampleContent.setTone("Sarcastic");
      sampleContent.setTopic("I really really enjoy writing tests");
      sampleContent.setAiMessage("Here's a sarcastic FB Post about how much fun writing unit tests are");
      sampleContent.setUserID("Albert Einstein");
      sampleContent.setAudience("Anyone who will listen");
      sampleContent.setWordCount(250);
      sampleContent.setPromptTokens(50);
      sampleContent.setCompletionTokens(50);
      sampleContent.setCompletionTokens(100);

        return sampleContent;
    }
}