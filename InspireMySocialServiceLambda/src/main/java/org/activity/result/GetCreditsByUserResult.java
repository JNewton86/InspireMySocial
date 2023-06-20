package org.activity.result;

import org.model.UserModel;

import java.util.Objects;

public class GetCreditsByUserResult {

    private final UserModel userModel;

    private GetCreditsByUserResult(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public String toString() {
        return "GetCreditsByUserResult{" +
                "userModel=" + userModel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetCreditsByUserResult that = (GetCreditsByUserResult) o;
        return Objects.equals(userModel, that.userModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userModel);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new GetCreditsByUserResult.Builder();
    }

    public static class Builder {

        private UserModel userModel;

        public Builder withUserModel(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }

        public GetCreditsByUserResult build() {
            return new GetCreditsByUserResult(userModel);
        }

    }
}
