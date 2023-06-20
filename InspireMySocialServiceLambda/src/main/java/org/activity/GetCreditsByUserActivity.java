package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.GetCreditsByUserRequest;
import org.activity.result.GetCreditsByUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.UserDao;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.model.UserModel;

import javax.inject.Inject;

public class GetCreditsByUserActivity {

    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * constructor for dagger to inject userDao.
     * @param userDao DAO class singleton to access DynamoDB table "Users"
     */
    @Inject
    public GetCreditsByUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Handle request method that is called by the lambda for this activity.
     * @param getCreditsByUserRequest request object created by the lambda with items from API request
     * @return user object with credit balance as an attribute
     */
    public GetCreditsByUserResult handleRequest(final GetCreditsByUserRequest getCreditsByUserRequest) {
        log.info("Recieved GetCreditRequest{}", getCreditsByUserRequest);
        String userEmail = getCreditsByUserRequest.getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        try {
            User user = userDao.getUser(userEmail);
            System.out.println("user from dao returned: " + user);
            UserModel userModel = new ModelConverter().toUserModel(user);
            return GetCreditsByUserResult.builder()
                    .withUserModel(userModel)
                    .build();
        } catch (UserNotFoundException e) {
            User newUser = new User();
            newUser.setUserEmail(getCreditsByUserRequest.getUserEmail());
            newUser.setCreditBalance(20);
            newUser.setName(getCreditsByUserRequest.getName());
            User user = userDao.saveUser(newUser);
           log.info("new user created and saved: " + newUser);
            UserModel userModel = new ModelConverter().toUserModel(user);
            return GetCreditsByUserResult.builder()
                    .withUserModel(userModel)
                    .build();
        }
    }
}
