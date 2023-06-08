package org.activity;

import org.Converter.ModelConverter;
import org.activity.request.UpdateUserCreditBalanceRequest;
import org.activity.result.UpdateUserCreditBalanceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.UserDao;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.model.UserModel;

import javax.inject.Inject;

public class UpdateUserCreditBalanceActivity {

    private final Logger log = LogManager.getLogger();

    private final UserDao userDao;

    @Inject
    public UpdateUserCreditBalanceActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    public UpdateUserCreditBalanceResult handleRequest(final UpdateUserCreditBalanceRequest
                       updateUserCreditBalanceRequest) {
        log.info("Received GetCreditRequest{}", updateUserCreditBalanceRequest);
        System.out.println("userEmail: " +updateUserCreditBalanceRequest.getUserEmail());
        String userEmail = updateUserCreditBalanceRequest.getUserEmail();
        if (userEmail == null || userEmail.isEmpty()) {
            throw new UserNotFoundException("Please provide a user's email!");
        }
        User user = userDao.getUser(userEmail);
        Integer creditbalance = user.getCreditBalance();
        creditbalance = creditbalance + updateUserCreditBalanceRequest.getCreditUsage();
        user.setCreditBalance(creditbalance);
        userDao.saveUser(user);
        UserModel userModel = new ModelConverter().toUserModel(user);
        return UpdateUserCreditBalanceResult.builder()
                .withUserModel(userModel)
                .build();
    }
}
