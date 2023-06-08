package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = org.activity.request.GetCreditsByUserRequest.Builder.class)
public class GetCreditsByUserRequest {

        private final String userEmail;

        public GetCreditsByUserRequest(String userEmail) {
            this.userEmail = userEmail;

        }

        public String getUserEmail() {
            return userEmail;
        }

        @Override
        public String toString() {
            return "GetContentForUserRequest{" +
                    "userEmail='" + userEmail + '\'' +
                    '}';
        }
        //CHECKSTYLE:OFF:Builder
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

            public GetCreditsByUserRequest build(){
                return new GetCreditsByUserRequest(userEmail);
            }
        }
}
