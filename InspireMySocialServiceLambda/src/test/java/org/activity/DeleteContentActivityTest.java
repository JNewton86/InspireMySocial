package org.activity;

import org.activity.request.DeleteContentRequest;
import org.activity.result.DeleteContentResult;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.model.ContentModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteContentActivityTest {
    private ContentDao contentDao;
    private DeleteContentActivity deleteContentActivity;

    @BeforeEach
    void setUp() {
        contentDao = Mockito.mock(ContentDao.class);
        deleteContentActivity = new DeleteContentActivity(contentDao);
    }

    @Test
    void handleRequest_throwsException_whenUserEmailIsNull() {
        DeleteContentRequest request = new DeleteContentRequest(null, "contentId");

        assertThrows(UserNotFoundException.class, () -> deleteContentActivity.handleRequest(request));
    }

    @Test
    void handleRequest_retrievesContent_usingUserEmailAndContentId() {
        DeleteContentRequest request = new DeleteContentRequest("user@example.com", "contentId");
        Content content = new Content();

        Mockito.when(contentDao.getContent(request.getUserId(), request.getContentId())).thenReturn(content);

        deleteContentActivity.handleRequest(request);

        Mockito.verify(contentDao).getContent(request.getUserId(), request.getContentId());
    }

    @Test
    void handleRequest_setsContentDeletedStatus_toTrue() {
        DeleteContentRequest request = new DeleteContentRequest("user@example.com", "contentId");
        Content content = new Content();

        Mockito.when(contentDao.getContent(request.getUserId(), request.getContentId())).thenReturn(content);

        deleteContentActivity.handleRequest(request);

        assertEquals(true, content.getDeleted());
    }

    @Test
    void handleRequest_savesUpdatedContent() {
        DeleteContentRequest request = new DeleteContentRequest("user@example.com", "contentId");
        Content content = new Content();

        Mockito.when(contentDao.getContent(request.getUserId(), request.getContentId())).thenReturn(content);

        deleteContentActivity.handleRequest(request);

        Mockito.verify(contentDao).saveContent(content);
    }

    @Test
    void handleRequest_returnsDeleteContentResult_withDeletedContentModel() {
        DeleteContentRequest request = new DeleteContentRequest("user@example.com", "contentId");
        Content content = new Content();

        Mockito.when(contentDao.getContent(request.getUserId(), request.getContentId())).thenReturn(content);

        DeleteContentResult result = deleteContentActivity.handleRequest(request);

        ContentModel contentModel = result.getContentModel();
        assertEquals(content.getTopic(), contentModel.getTopic());
        assertEquals(content.getAiMessage(), contentModel.getAiMessage());

    }
}
