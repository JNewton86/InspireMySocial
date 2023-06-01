package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.GetContentForUserRequest;
import org.activity.result.GetContentForUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.exception.UserNotFoundException;
import org.model.ContentModel;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetContentForUserActivity {

    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;

    @Inject
    public GetContentForUserActivity(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    /**
     * This method is the handleRequest method for the Activity, it retrieves a list of Content for the user.
     * @param getContentForUserRequest is a request passed to the method from the Lambda for the Activity
     * @return a GetContentForUserResult which is primarily comprised of a List<ContentModel> that is returned by
     * the API to the front end client for the specified user.
     **/
    public GetContentForUserResult handleRequest(final GetContentForUserRequest getContentForUserRequest) {
        log.info("Recieved GetContentRequest{}", getContentForUserRequest);
        String userEmail = getContentForUserRequest.getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        List<Content> listOfContent = contentDao.getAllContentForUser(userEmail);
        if(listOfContent.isEmpty()) {
            return GetContentForUserResult.builder()
                    .withContentModelList(new ArrayList<>())
                    .build();
        }
        log.info("made it past contentdao.getAllContentForUser List<Content> = {}", listOfContent.toString());
        List<ContentModel> contentModelList = new ModelConverter().toContentModelList(listOfContent);
        log.info("converted from content to contentModelList");
        return GetContentForUserResult.builder()
                .withContentModelList(contentModelList)
                .build();
    }

}
