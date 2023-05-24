package org.Converter;

import org.dynamodb.models.Content;
import org.model.ContentModel;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    /**
     * This is a method to convert data from a DynamoDB table for storing Content to a API Model for content.
     * @param content an instance of the dynamo model object content
     * @return ContentModel which is API model for the GetContent and CreateContent endpoints
     */
    public ContentModel toContentModel(Content content) {
        return ContentModel.builder()
                .withUserId(content.getUserID())
                .withContentId(content.getContentId())
                .withContentType(content.getContentType())
                .withTopic(content.getTopic())
                .withAiMessage(content.getAiMessage())
                .withIsDeleted(content.getDeleted())
                .build();
    }
}
