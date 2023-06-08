package org.model;

import java.util.Objects;

public class UserModel {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final Integer creditBalance;


    public UserModel(String userId, String firstName, String lastName, Integer creditBalance) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditBalance = creditBalance;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getCreditBalance() {
        return creditBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(userId, userModel.userId) && Objects.equals(firstName, userModel.firstName) && Objects.equals(lastName, userModel.lastName) && Objects.equals(creditBalance, userModel.creditBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, creditBalance);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String userId;
        private String firstName;
        private String lastName;
        private Integer creditBalance;

        public  Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public  Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public  Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public  Builder withCreditBalance(Integer creditBalance) {
            this.creditBalance = creditBalance;
            return this;
        }

        public UserModel build(){
            return new UserModel(userId,firstName,lastName,creditBalance);
        }

    }
}
