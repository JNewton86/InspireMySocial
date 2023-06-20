package org.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.metrics.MetricsPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDaoTest {

    private DynamoDBMapper dynamoDbMapper;
    private MetricsPublisher metricsPublisher;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        dynamoDbMapper = Mockito.mock(DynamoDBMapper.class);
        metricsPublisher = Mockito.mock(MetricsPublisher.class);
        userDao = new UserDao(dynamoDbMapper, metricsPublisher);
    }

    @Test
    void getUser_returnsUser_whenUserExists() {
        String userEmail = "user@example.com";
        User sampleUser = new User();
        sampleUser.setCreditBalance(100);
        sampleUser.setUserEmail(userEmail);

        Mockito.when(dynamoDbMapper.load(User.class, userEmail)).thenReturn(sampleUser);

        User returnedUser = userDao.getUser(userEmail);

        assertEquals(sampleUser.getCreditBalance(), returnedUser.getCreditBalance());
        assertEquals(sampleUser.getUserEmail(), returnedUser.getUserEmail());
    }

    @Test
    void getUser_throwsUserNotFoundException_whenUserDoesNotExist() {
        String userEmail = "nonexistent@example.com";
        Mockito.when(dynamoDbMapper.load(User.class, userEmail)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userDao.getUser(userEmail));
    }

    @Test
    void saveUser_callsDynamoDbMapperSave() {
        User user = new User(); // A sample User object

        userDao.saveUser(user);

        Mockito.verify(dynamoDbMapper).save(user);
    }
}