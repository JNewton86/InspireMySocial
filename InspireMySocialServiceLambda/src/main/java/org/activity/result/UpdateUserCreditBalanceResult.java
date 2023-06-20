package org.activity.result;

import org.model.UserModel;

import java.util.Objects;

public class UpdateUserCreditBalanceResult {

    private final UserModel userModel;

    /**
     * Constructor for class.
     * @param userModel passed in by userModel converter
     */
    public UpdateUserCreditBalanceResult(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public String toString() {
        return "UpdateUserCreditBalanceResult{" +
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
        UpdateUserCreditBalanceResult that = (UpdateUserCreditBalanceResult) o;
        return Objects.equals(userModel, that.userModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userModel);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new UpdateUserCreditBalanceResult.Builder();
    }

    public static class Builder {

        private UserModel userModel;

        public Builder withUserModel(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }

        public UpdateUserCreditBalanceResult build() {
            return new UpdateUserCreditBalanceResult(userModel);
        }
    }
}
