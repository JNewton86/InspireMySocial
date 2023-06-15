package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = org.activity.request.GetCreditsByUserRequest.Builder.class)
public class GetCreditsByUserRequest {

        private final String userEmail;

        private final String name;

        public GetCreditsByUserRequest(String userEmail, String name) {
            this.userEmail = userEmail;
            this.name = name;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getName() {
            return name;
        }

    @Override
    public String toString() {
        return "GetCreditsByUserRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
        public static Builder builder(){
            return new Builder();
        }

        @JsonPOJOBuilder
        public static class Builder{
            private String userEmail;
            private String name;

            public Builder withUserEmail(String userEmail){
                this.userEmail = userEmail;
                return this;
            }

            public Builder withName(String name){
                this.name = name;
                return this;
            }

            public GetCreditsByUserRequest build(){
                return new GetCreditsByUserRequest(userEmail, name);
            }
        }
}
