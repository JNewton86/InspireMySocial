package org.activity;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;

import javax.inject.Inject;

/**
 * Implementation of the CreateContentActivity for the InspireMySocial CreateContent API.
 * <p>
 * This API allows the customer to create new social media content.
 */
public class CreateContentActivity {
    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiAPIActivity openAiAPIActivity;

    @Inject
    public CreateContentActivity(ContentDao contentDao, OpenAiAPIActivity openAiAPIActivity) {
        this.contentDao = contentDao;
        this.openAiAPIActivity = openAiAPIActivity;

    }
    public CreateContentResult handleRequest(final CreateContentRequest createContentRequest){
        log.info("Receieved CreateContent Request {}", createContentRequest);
        openAiAPIActivity
    }
}
