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

    @Inject
    public GetCreditsByUserActivity(UserDao userDao){
        this.userDao=userDao;
    }

    public GetCreditsByUserResult handleRequest(final GetCreditsByUserRequest getCreditsByUserRequest) {
        log.info("Recieved GetCreditRequest{}", getCreditsByUserRequest);
        System.out.println("userEmail: " +getCreditsByUserRequest.getUserEmail());
        String userEmail = getCreditsByUserRequest.getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        User user = userDao.getUser(userEmail);
        UserModel userModel = new ModelConverter().toUserModel(user);
        return GetCreditsByUserResult.builder()
                .withUserModel(userModel)
                .build();
    }
}
