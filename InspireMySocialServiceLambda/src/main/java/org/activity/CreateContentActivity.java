package org.activity;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.Converter.ModelConverter;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.models.Content;
import org.model.ContentModel;
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

    /**
     *  Constructor for the CreateContentActivity Class.
     * @param contentDao a data acccess only class that access DynamoDBTable storing content
     * @param openAiDao a data access only class that leverages a community service for accessing OpenAI's API
     */
    @Inject
    public CreateContentActivity(ContentDao contentDao, OpenAiDao openAiDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;
    }

    /**
     * A method that accepts a createContentRequest and completes the logic required to return a CreateContentResult.
     * @param createContentRequest request received via the API endpoint
     * @return returns a result object built from the content object in the below method
     */
    public CreateContentResult handleRequest(final CreateContentRequest createContentRequest) {
        log.info("Receieved CreateContent Request {}", createContentRequest);
        System.out.println("request recieved in Create Content Activity");
        ChatCompletionResult post = openAiDao.createContent(createContentRequest);
        //Create and instantiate the Dynamo Object
        Content newContent = new Content();
        newContent.setUserID(createContentRequest.getUserId());
        newContent.setContentType(createContentRequest.getContentType());
        newContent.setContentId(post.getId());
        newContent.setTone(createContentRequest.getTone());
        newContent.setAudience(createContentRequest.getAudience());
        newContent.setTopic(createContentRequest.getTopic());
        newContent.setWordCount(createContentRequest.getWordCount());
        newContent.setDeleted(false);
        newContent.setAiMessage(post.getChoices().get(0).getMessage().getContent());
        long promptUsage = post.getUsage().getPromptTokens();
        Integer promptTokensInt = (int) promptUsage;
        newContent.setPromptTokens(promptTokensInt);
        long completionUsage = post.getUsage().getCompletionTokens();
        Integer completionUsageInt = (int) completionUsage;
        newContent.setCompletionTokens(completionUsageInt);
        newContent.setTotalTokens(completionUsageInt + promptTokensInt);
        //Save the Content Object
        contentDao.saveContent(newContent);
        ContentModel contentModel = new ModelConverter().toContentModel(newContent);
        return CreateContentResult.builder()
                .withContentModel(contentModel)
                .build();
    }
}
