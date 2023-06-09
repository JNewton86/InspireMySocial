package org.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.util.List;


/**
 * Represents a social media content returned by OpenAI in the ims_socialmediacontent dynamo table.
 */
@DynamoDBTable(tableName = "ims_socialMediaContent")
public class Content {

    private String userID;
    private String contentId;
    private String contentType;
    private String tone;
    private String topic;
    private String audience;
    private Integer wordCount;
    private Boolean isDeleted;
    private String aiMessage;
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;

    private List<String> images;

    @DynamoDBHashKey(attributeName = "userEmail")
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @DynamoDBAttribute(attributeName = "contentType")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    @DynamoDBAttribute(attributeName = "tone")
    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
    @DynamoDBRangeKey(attributeName = "contentId")
    public String getContentId() {
        return contentId;
    }


    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    @DynamoDBAttribute(attributeName = "topic")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    @DynamoDBAttribute(attributeName = "audience")
    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
    @DynamoDBAttribute(attributeName = "wordCount")
    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "isDeleted")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    @DynamoDBAttribute(attributeName = "aiMessage")
    public String getAiMessage() {
        return aiMessage;
    }

    public void setAiMessage(String aiMessage) {
        this.aiMessage = aiMessage;
    }
    @DynamoDBAttribute(attributeName = "promptTokens")
    public Integer getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }
    @DynamoDBAttribute(attributeName = "completionTokens")
    public Integer getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }
    @DynamoDBAttribute(attributeName = "totalTokens")
    public Integer getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }

    @DynamoDBAttribute(attributeName = "images")
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Content{" +
                "userID='" + userID + '\'' +
                ", contentId='" + contentId + '\'' +
                ", contentType='" + contentType + '\'' +
                ", tone='" + tone + '\'' +
                ", topic='" + topic + '\'' +
                ", audience='" + audience + '\'' +
                ", wordCount=" + wordCount +
                ", isDeleted=" + isDeleted +
                ", aiMessage='" + aiMessage + '\'' +
                ", promptTokens=" + promptTokens +
                ", completionTokens=" + completionTokens +
                ", totalTokens=" + totalTokens +
                ", images=" + images +
                '}';
    }
}
