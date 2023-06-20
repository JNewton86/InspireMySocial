package org.activity;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.Converter.ModelConverter;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.UserDao;
import org.dynamodb.models.Content;
import org.dynamodb.models.User;
import org.exception.InsufficientCreditsException;
import org.model.ContentModel;
import org.openaiservice.OpenAiDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the CreateContentActivity for the InspireMySocial CreateContent API.
 * <p>
 * This API allows the customer to create new social media content.
 */
public class CreateContentActivity {
    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiDao openAiDao;
    private final UserDao userDao;

    /**
     * Constructor for the CreateContentActivity Class.
     *
     * @param contentDao a data acccess only class that access DynamoDBTable storing content
     * @param openAiDao  a data access only class that leverages a community service for accessing OpenAI's API
     * @param userDao
     */
    @Inject
    public CreateContentActivity(ContentDao contentDao, OpenAiDao openAiDao, UserDao userDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;
        this.userDao = userDao;
    }

    /**
     * A method that accepts a createContentRequest and completes the logic required to return a CreateContentResult.
     *
     * @param createContentRequest request received via the API endpoint
     * @return returns a result object built from the content object in the below method
     */
    public CreateContentResult handleRequest(final CreateContentRequest createContentRequest) {
        // Print lines for bug hunt
        System.out.println("*** congrats, you made it to the handleRequest method in CreateContentActivity ***");
        System.out.println("The CreateContentActivity request was : " + createContentRequest.toString());
        System.out.println("request received in Create Content Activity");

        // log.
        log.info("Receieved CreateContent Request {}", createContentRequest);
        User user = userDao.getUser(createContentRequest.getUserId());
        if(user.getCreditBalance() == 0){
            throw new InsufficientCreditsException("Insufficient Credits Remaining");
        }
        // Call to openAiDao to create content.
        try {
            ChatCompletionResult post = openAiDao.createContent(createContentRequest);
            // Create and instantiate the Dynamo Object
            System.out.println("*** the open ai call returned: " + post.toString() + " ***");
            Content newContent = new Content();
            newContent.setUserID(createContentRequest.getUserId());
            newContent.setContentType(createContentRequest.getContentType());
            System.out.println("****** The content ID from OpenAI Call is " + post.getId() + " *****") ;
            newContent.setContentId(post.getId());
            newContent.setTone(createContentRequest.getTone());
            newContent.setAudience(createContentRequest.getAudience());
            newContent.setTopic(createContentRequest.getTopic());
            newContent.setWordCount(createContentRequest.getWordCount());
            newContent.setDeleted(false);
            List<String> images = new ArrayList<>();
            newContent.setImages(images);
            newContent.setAiMessage(post.getChoices().get(0).getMessage().getContent());
            long promptUsage = post.getUsage().getPromptTokens();
            Integer promptTokensInt = (int) promptUsage;
            newContent.setPromptTokens(promptTokensInt);
            long completionUsage = post.getUsage().getCompletionTokens();
            Integer completionUsageInt = (int) completionUsage;
            newContent.setCompletionTokens(completionUsageInt);
            newContent.setTotalTokens(completionUsageInt + promptTokensInt);
            System.out.println("Content object to save to Dynamo: " + newContent.toString());
            // Save the Content Object
            contentDao.saveContent(newContent);

            // Dynamo to API Model convertion call
            ContentModel contentModel = new ModelConverter().toContentModel(newContent);
            // Sample contnet from bug hunt.
            // ContentModel contentModel = new ContentModel("Test","TestId", "FaceBook", "bug hunts", "Are fun", false);
            return CreateContentResult.builder()
                    .withContentModel(contentModel)
                    .build();

        } catch (Exception e) {
            System.out.println("this is line 87 of the activity" + e);
            throw e;
        }
    }
}




