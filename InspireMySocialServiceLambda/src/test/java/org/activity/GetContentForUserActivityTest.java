package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.GetContentForUserRequest;
import org.activity.result.GetContentForUserResult;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.metrics.MetricsPublisher;
import org.mockito.Mock;
import org.model.ContentModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetContentForUserActivityTest {
    @Mock
    private ContentDao contentDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private GetContentForUserActivity getContentForUserActivity;

    @BeforeEach
    private void setup(){
        initMocks(this);
        getContentForUserActivity= new GetContentForUserActivity(contentDao);
    }

    @Test
    void handleRequest_validRequest_result(){
        // GIVEN
        String email = "test@test.com";
        GetContentForUserRequest getContentForUserRequest = GetContentForUserRequest.builder()
                .withUserEmail(email)
                .build();

        Content content = new Content();
        content.setUserID("test@test.com");
        content.setContentType("fbPost");
        content.setContentId("testContent12345");
        content.setTotalTokens(400);
        content.setCompletionTokens(250);
        content.setPromptTokens(150);
        content.setDeleted(false);
        content.setAiMessage("Dolphins are the coolest animal on earth, besides dogs");
        content.setWordCount(250);
        content.setTopic("why are dolphins so cool?");
        content.setTone("friendly");
        List<Content> contentList = new ArrayList<>();
        contentList.add(content);
        List<ContentModel> contentModelList = new ModelConverter().toContentModelList(contentList);
        GetContentForUserResult getContentForUserResult = GetContentForUserResult.builder()
                .withContentModelList(contentModelList)
                .build();

        when(contentDao.getAllContentForUser(email)).thenReturn(contentList);

        // WHEN
        GetContentForUserResult result = getContentForUserActivity.handleRequest(getContentForUserRequest);

        // THEN
        assertEquals(result, getContentForUserResult);
    }

    @Test
    void handleRequest_inValidRequest_throwsException(){
        // GIVEN
        String email1 = "";
        GetContentForUserRequest getContentForUserRequest1 = GetContentForUserRequest.builder()
                .withUserEmail(email1)
                .build();
        when(contentDao.getAllContentForUser(email1)).thenThrow(new UserNotFoundException());
        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> getContentForUserActivity
                .handleRequest(getContentForUserRequest1));
    }

    @Test
    void handleRequest_inValidRequestNull_throwsException(){
        // GIVEN
        String email1 = null;
        GetContentForUserRequest getContentForUserRequest1 = GetContentForUserRequest.builder()
                .withUserEmail(email1)
                .build();
        when(contentDao.getAllContentForUser(email1)).thenThrow(new UserNotFoundException());
        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> getContentForUserActivity
                .handleRequest(getContentForUserRequest1));
    }

    @Test
    void handleReqeust_validEmailNoContent_returnEmptyList(){
        // GIVEN
        String email2 = "test@test.com";
        GetContentForUserRequest getContentForUserRequest2 = GetContentForUserRequest.builder()
                .withUserEmail(email2)
                .build();
        when(contentDao.getAllContentForUser(email2)).thenReturn(new ArrayList<>());

        // WHEN
        GetContentForUserResult result = getContentForUserActivity.handleRequest(getContentForUserRequest2);

        // THEN
        assertEquals(result.getContentList(), new ArrayList<>());
    }
}