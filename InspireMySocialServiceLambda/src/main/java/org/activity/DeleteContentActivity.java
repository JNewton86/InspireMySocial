package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.DeleteContentRequest;
import org.activity.request.GetContentForUserRequest;
import org.activity.result.CreateContentResult;
import org.activity.result.DeleteContentResult;
import org.activity.result.GetContentForUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.exception.UserNotFoundException;
import org.model.ContentModel;

import javax.inject.Inject;

public class DeleteContentActivity {

    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;

    @Inject
    public DeleteContentActivity(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    public DeleteContentResult handleRequest(final DeleteContentRequest deleteContentRequest) {
        log.info("Recieved GetContentRequest{}", deleteContentRequest);
        String userEmail = deleteContentRequest.getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        Content oldContent = contentDao.getContent(deleteContentRequest.getUserEmail(),
                deleteContentRequest.getContentId());
        oldContent.setDeleted(true);
        contentDao.saveContent(oldContent);
        ContentModel contentModel = new ModelConverter().toContentModel(oldContent);
        return DeleteContentResult.builder()
                .withContentModel(contentModel)
                .build();
    }
}
