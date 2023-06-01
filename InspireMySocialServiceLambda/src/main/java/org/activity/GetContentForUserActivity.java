package org.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.Converter.ModelConverter;
import org.activity.request.GetContentForUserRequest;
import org.activity.result.GetContentForUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.exception.UserNotFoundException;
import org.lambda.LambdaActivityRunner;
import org.lambda.LambdaRequest;
import org.lambda.LambdaResponse;
import org.model.ContentModel;

import javax.inject.Inject;
import java.util.List;

public class GetContentForUserActivity{

    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;

    @Inject
    public GetContentForUserActivity(ContentDao contentDao){this.contentDao = contentDao;}

    public GetContentForUserResult handleRequest(final GetContentForUserRequest getContentForUserRequest){
        log.info("Recieved GetContentRequest{}", getContentForUserRequest);
        String userEmail = getContentForUserRequest.getUserEmail();

        if(userEmail == null || userEmail.isEmpty()){
            throw new UserNotFoundException("Please provide a user's email!");
        }
        List<Content> listOfContent = contentDao.getAllContentForUser(userEmail);
        log.info("made it past contentdao.getAllContentForUser List<Content> = {}", listOfContent.toString());
        List<ContentModel> contentModelList = new ModelConverter().toContentModelList(listOfContent);
        log.info("converted from content to contentModelList");
        return GetContentForUserResult.builder()
                .withContentModelList(contentModelList)
                .build();
    }

}
