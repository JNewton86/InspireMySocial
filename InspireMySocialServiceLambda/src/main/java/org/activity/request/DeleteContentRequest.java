package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteContentRequest.Builder.class)
public class DeleteContentRequest {

    private String userId;
    private String contentId;

    /**
     * constructor.
     * @param userEmail user's email address from Cognito Claim
     * @param contentId contentId from API call
     */
    public DeleteContentRequest(String userEmail, String contentId) {
        this.userId = userEmail;
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContentId() {
        return contentId;
    }

    @Override
    public String toString() {
        return "DeleteContentRequest{" +
                "userEmail='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
        public static org.activity.request.DeleteContentRequest.Builder builder(){
            return new org.activity.request.DeleteContentRequest.Builder();
        }

        @JsonPOJOBuilder
        public static class Builder{
            private String userId;
            private String contentId;

            public DeleteContentRequest.Builder withUserId(String userId){
                this.userId = userId;
                return this;
            }

            public DeleteContentRequest.Builder withContentId(String contentId){
                this.contentId = contentId;
                return this;
            }

            public org.activity.request.DeleteContentRequest build(){
                return new org.activity.request.DeleteContentRequest(userId,contentId);
            }
        }

    }
