package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = org.activity.request.DeleteContentRequest.Builder.class)
public class DeleteContentRequest {

        private final String userEmail;
        private final String contentId;

        public DeleteContentRequest(String userEmail, String contentId) {
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
        return "DeleteContentRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
        public static org.activity.request.DeleteContentRequest.Builder builder(){
            return new org.activity.request.DeleteContentRequest.Builder();
        }

        @JsonPOJOBuilder
        public static class Builder{
            private String userEmail;
            private String contentId;

            public DeleteContentRequest.Builder withUserEmail(String userEmail){
                this.userEmail = userEmail;
                return this;
            }

            public DeleteContentRequest.Builder withContentId(String contentId){
                this.contentId = contentId;
                return this;
            }

            public org.activity.request.DeleteContentRequest build(){
                return new org.activity.request.DeleteContentRequest(userEmail,contentId);
            }
        }

    }
