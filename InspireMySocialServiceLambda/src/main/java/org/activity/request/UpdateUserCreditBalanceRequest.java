package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import software.amazon.awssdk.services.sso.endpoints.internal.Value;

import java.util.Objects;

@JsonDeserialize(builder = org.activity.request.UpdateUserCreditBalanceRequest.Builder.class)
public class UpdateUserCreditBalanceRequest {

        private String userEmail;
        private Integer creditUsage;

        public UpdateUserCreditBalanceRequest(String userEmail, Integer creditUsage) {
            this.userEmail = userEmail;
            this.creditUsage = creditUsage;

        }

        public String getUserEmail() {
            return userEmail;
        }

        public Integer getCreditUsage() {
            return creditUsage;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserCreditBalanceRequest that = (UpdateUserCreditBalanceRequest) o;
        return Objects.equals(userEmail, that.userEmail) && Objects.equals(creditUsage, that.creditUsage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, creditUsage);
    }

    //CHECKSTYLE:OFF:Builder
        public static Builder builder(){
            return new Builder();
        }

        @JsonPOJOBuilder
        public static class Builder{
            private String userEmail;
            private Integer creditUsage;

            public Builder withUserEmail(String userEmail){
                this.userEmail = userEmail;
                return this;
            }

            public Builder withCreditUsage(Integer creditUsage) {
                this.creditUsage = creditUsage;
                return this;
            }

            public UpdateUserCreditBalanceRequest build(){
                return new UpdateUserCreditBalanceRequest(userEmail,creditUsage);
            }
        }
    }
