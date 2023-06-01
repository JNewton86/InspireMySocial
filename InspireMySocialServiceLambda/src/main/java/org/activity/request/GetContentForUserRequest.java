package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetContentForUserRequest.Builder.class)
public class GetContentForUserRequest {
   private final String userEmail;

    public GetContentForUserRequest(String userEmail) {this.userEmail = userEmail; }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
        return "GetContentForUserRequest{" +
                "userEmail='" + userEmail + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder{
        private String userEmail;

        public Builder withUserEmail(String userEmail){
            this.userEmail = userEmail;
            return this;
        }

        public GetContentForUserRequest build(){
            return new GetContentForUserRequest(userEmail);
        }
    }

}
