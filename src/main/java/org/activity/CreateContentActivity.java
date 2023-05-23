package org.activity;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.model.ChatCompletion;
import org.openaiservice.OpenAiDao;

import javax.inject.Inject;

/**
 * Implementation of the CreateContentActivity for the InspireMySocial CreateContent API.
 * <p>
 * This API allows the customer to create new social media content.
 */
public class CreateContentActivity {
    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiDao openAiDao;

    @Inject
    public CreateContentActivity(ContentDao contentDao, OpenAiDao openAiDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;


    }
    public CreateContentResult handleRequest(final CreateContentRequest createContentRequest){
        log.info("Receieved CreateContent Request {}", createContentRequest);
        String post = openAiDao.createContent(createContentRequest);
        Content newContent = new Content();
        newContent.setUserID(createContentRequest.getUserId());
        newContent.setContentType(createContentRequest.getContentType());
        newContent.setTone(createContentRequest.getTone());
        newContent.setAudience(createContentRequest.getAudience());
        newContent.setTopic(createContentRequest.getTopic());
        newContent.setWordCount(createContentRequest.getWordCount());
        contentDao.savePlaylist(newContent);

        CreateContentResult test = null;
        return test;
    }
}
