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
        try {
            User user = userDao.getUser(userEmail);
            System.out.println("user from dao returned: " + user);
            UserModel userModel = new ModelConverter().toUserModel(user);
            return GetCreditsByUserResult.builder()
                    .withUserModel(userModel)
                    .build();
        }
        catch (UserNotFoundException e){
            User newUser = new User();
            newUser.setUserEmail(getCreditsByUserRequest.getUserEmail());
            newUser.setCreditBalance(20);
            newUser.setName(getCreditsByUserRequest.getName());
            User user = userDao.saveUser(newUser);
            System.out.println("new user created and saved: " + newUser);
            UserModel userModel = new ModelConverter().toUserModel(user);
            return GetCreditsByUserResult.builder()
                    .withUserModel(userModel)
                    .build();
        }
//        User user = userDao.getUser(userEmail);
//        // If user not in table then prepopulate with 20 credits, and reset user from network call to newUser value.
//        if (user == null){
//            User newUser = new User();
//            newUser.setUserEmail(getCreditsByUserRequest.getUserEmail());
//            newUser.setCreditBalance(20);
//            newUser.setName(getCreditsByUserRequest.getName());
//            userDao.saveUser(newUser);
//            System.out.println("new user created and saved: " + newUser);
//            UserModel userModel = new ModelConverter().toUserModel(newUser);
//            return GetCreditsByUserResult.builder()
//                    .withUserModel(userModel)
//                    .build();
//        }
//        UserModel userModel = new ModelConverter().toUserModel(user);
//        return GetCreditsByUserResult.builder()
//                .withUserModel(userModel)
//                .build();
    }
}
