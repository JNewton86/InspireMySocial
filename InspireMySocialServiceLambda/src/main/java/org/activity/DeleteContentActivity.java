package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.DeleteContentRequest;
import org.activity.result.DeleteContentResult;
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

    /**
     * Constructor used by dagger.
     * @param contentDao DAO class used to access the content table on DynamoDB
     */
    @Inject
    public DeleteContentActivity(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    /**
     * This is a method that handles the request for the soft delete of the content.
     * @param deleteContentRequest which is recieved in the lambda via the API call, contains user and contentId
     * @return copy of the deleted content.
     */
    public DeleteContentResult handleRequest(final DeleteContentRequest deleteContentRequest) {
        log.info("Recieved GetContentRequest{}", deleteContentRequest);
        String userEmail = deleteContentRequest.getUserId();
        if (userEmail == null) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        Content oldContent = contentDao.getContent(deleteContentRequest.getUserId(),
                deleteContentRequest.getContentId());
        oldContent.setDeleted(true);
        contentDao.saveContent(oldContent);
        ContentModel contentModel = new ModelConverter().toContentModel(oldContent);
        return DeleteContentResult.builder()
                .withContentModel(contentModel)
                .build();
    }
}
