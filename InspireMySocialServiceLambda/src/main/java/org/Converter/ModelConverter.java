package org.Converter;

import org.dynamodb.models.Content;
import org.dynamodb.models.User;
import org.model.ContentModel;
import org.model.UserModel;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * This is a method to convert a list of data from a DynamoDB table for storing Content to a List of Models for the
     * content.
     * @param contentList a List of the dynamo object content
     * @return ContentModel is a List<ContentModel> which is List of the API model for the GetContent
     */
    public List<ContentModel> toContentModelList(List<Content> contentList) {
        List<ContentModel> contentModels = new ArrayList<>();

        for (Content coolContent : contentList) {
            contentModels.add(toContentModel(coolContent));
        }

        return contentModels;
    }

    /**
     * This is a method to convert a DynamoDb user to an API user model.
     * @param user , which is a DynamoDBModel of a user
     * @return returns a userModel for API
     */
    public UserModel toUserModel(User user) {
        return UserModel.builder()
                .withUserId(user.getUserEmail())
                .withName(user.getName())
                .withCreditBalance(user.getCreditBalance())
                .build();
    }
}
