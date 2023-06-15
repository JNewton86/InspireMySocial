package org.model;

import java.util.Objects;

public class UserModel {

    private final String userId;
    private final String name;

    private final Integer creditBalance;


    public UserModel(String userId, String name, Integer creditBalance) {
        this.userId = userId;
        this.name = name;
       this.creditBalance = creditBalance;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }


    public Integer getCreditBalance() {
        return creditBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(userId, userModel.userId) && Objects.equals(name, userModel.name) && Objects.equals(creditBalance, userModel.creditBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, creditBalance);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String userId;
        private String name;

        private Integer creditBalance;

        public  Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public  Builder withName(String name) {
            this.name = name;
            return this;
        }

        public  Builder withCreditBalance(Integer creditBalance) {
            this.creditBalance = creditBalance;
            return this;
        }

        public UserModel build(){
            return new UserModel(userId,name,creditBalance);
        }

    }
}
