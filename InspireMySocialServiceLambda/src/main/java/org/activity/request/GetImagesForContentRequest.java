package org.activity.request;

import com.amazonaws.internal.config.Builder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetImagesForContentRequest.Builder.class)
public class GetImagesForContentRequest {

    private final String userEmail;
    private final String contentId;

    /**
     * Constructor.
     * @param userEmail user email as a string that is primary key for dynamoDB request
     * @param contentId string contentId that is unique identifier generated by OpenAI for each content piece, used
     * as range key for DynamoDB call
     */
    public GetImagesForContentRequest(String userEmail, String contentId) {
        this.userEmail = userEmail;
        this.contentId = contentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getContentId() {
        return contentId;
    }

    @Override
    public String toString() {
        return "GetImagesForContentRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder(){
        return new Builder();
    }


    @JsonPOJOBuilder
    public static class Builder{
        private String userEmail;
        private String contentId;

        public Builder withUserEmail(String userEmail){
            this.userEmail = userEmail;
            return this;
        }

        public Builder withContentId(String contentId){
            this.contentId = contentId;
            return this;
        }

        public GetImagesForContentRequest build(){ return new GetImagesForContentRequest(userEmail,contentId);}
    }
}
