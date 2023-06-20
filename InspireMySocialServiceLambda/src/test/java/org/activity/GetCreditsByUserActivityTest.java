package org.activity;

import org.activity.request.GetCreditsByUserRequest;
import org.activity.result.GetCreditsByUserResult;
import org.dynamodb.UserDao;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.model.UserModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetCreditsByUserActivityTest {

    private UserDao userDao;
    private GetCreditsByUserActivity getCreditsByUserActivity;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        getCreditsByUserActivity = new GetCreditsByUserActivity(userDao);
    }

    @Test
    void handleRequest_throwsException_whenUserEmailIsNull() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest(null, "Name");

        assertThrows(UserNotFoundException.class, () -> getCreditsByUserActivity.handleRequest(request));
    }

    @Test
    void handleRequest_throwsException_whenUserEmailIsEmpty() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest("", "Name");

        assertThrows(UserNotFoundException.class, () -> getCreditsByUserActivity.handleRequest(request));
    }

    @Test
    void handleRequest_retrievesUser_usingUserEmail() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest("user@example.com", "Name");
        User user = new User();

        Mockito.when(userDao.getUser(request.getUserEmail())).thenReturn(user);

        getCreditsByUserActivity.handleRequest(request);

        Mockito.verify(userDao).getUser(request.getUserEmail());
    }

    @Test
    void handleRequest_returnsGetCreditsByUserResult_withUserModel_whenUserExists() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest("user@example.com", "Name");
        User user = new User();

        Mockito.when(userDao.getUser(request.getUserEmail())).thenReturn(user);

        GetCreditsByUserResult result = getCreditsByUserActivity.handleRequest(request);

        UserModel userModel = result.getUserModel();
        assertEquals(user.getUserEmail(), userModel.getUserId());
        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getCreditBalance(), userModel.getCreditBalance());
    }

    @Test
    void handleRequest_createsAndSavesNewUser_whenUserDoesNotExist() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest("user@example.com", "Name");

        Mockito.when(userDao.getUser(request.getUserEmail())).thenThrow(new UserNotFoundException("User not found"));
        Mockito.when(userDao.saveUser(Mockito.any(User.class))).thenAnswer(invocation ->
                invocation.getArgument(0));
        GetCreditsByUserResult result = getCreditsByUserActivity.handleRequest(request);

        Mockito.verify(userDao).saveUser(Mockito.any(User.class));
    }

    @Test
    void handleRequest_returnsGetCreditsByUserResult_withDefaultCreditsBalance_forNewUser() {
        GetCreditsByUserRequest request = new GetCreditsByUserRequest("user@example.com", "Name");
        User newUser = new User();
        newUser.setUserEmail(request.getUserEmail());
        newUser.setName(request.getName());
        newUser.setCreditBalance(20);

        Mockito.when(userDao.getUser(request.getUserEmail())).thenThrow(new UserNotFoundException("User not found"));
        Mockito.when(userDao.saveUser(Mockito.any(User.class))).thenReturn(newUser);

        GetCreditsByUserResult result = getCreditsByUserActivity.handleRequest(request);

        UserModel userModel = result.getUserModel();
        assertEquals(newUser.getUserEmail(), userModel.getUserId());
        assertEquals(newUser.getName(), userModel.getName());
        assertEquals(newUser.getCreditBalance(), userModel.getCreditBalance());
    }
}