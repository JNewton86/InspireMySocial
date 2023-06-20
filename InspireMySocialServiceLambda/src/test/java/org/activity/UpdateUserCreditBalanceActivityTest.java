package org.activity;

import org.activity.request.UpdateUserCreditBalanceRequest;
import org.activity.result.UpdateUserCreditBalanceResult;
import org.dynamodb.UserDao;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.model.UserModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateUserCreditBalanceActivityTest {

    private UserDao userDao;
    private UpdateUserCreditBalanceActivity updateUserActivity;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        updateUserActivity = new UpdateUserCreditBalanceActivity(userDao);
    }

    @Test
    void handleRequest_throwsException_whenEmailNotProvided() {
        UpdateUserCreditBalanceRequest request = new UpdateUserCreditBalanceRequest(null, 10);
        assertThrows(UserNotFoundException.class, () -> updateUserActivity.handleRequest(request));
    }

    @Test
    void handleRequest_updatesUserCreditBalance() {
        String userEmail = "user@example.com";
        int initialCreditBalance = 50;
        int creditUsage = -10;
        int expectedCreditBalance = initialCreditBalance + creditUsage;

        User user = new User();
        user.setUserEmail(userEmail);
        user.setCreditBalance(initialCreditBalance);

        Mockito.when(userDao.getUser(userEmail)).thenReturn(user);

        UpdateUserCreditBalanceRequest request = new UpdateUserCreditBalanceRequest(userEmail, creditUsage);
        UpdateUserCreditBalanceResult result = updateUserActivity.handleRequest(request);

        UserModel userModel = result.getUserModel();
        assertEquals(expectedCreditBalance, userModel.getCreditBalance());

        // Verify that the updated user object is saved
        Mockito.verify(userDao, Mockito.times(1)).saveUser(Mockito.any(User.class));
    }
}