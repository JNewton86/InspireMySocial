package org.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.metrics.MetricsPublisher;

import javax.inject.Inject;

public class UserDao {

    private final DynamoDBMapper dynamoDbMapper;

    private final MetricsPublisher metricsPublisher;

    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

public User getUser(String userEmail){
        User user = this.dynamoDbMapper.load(User.class, userEmail);

        if (user == null) {
            throw new UserNotFoundException("Could not find the user with userEmail: " + userEmail);
        }
        return user;
}

public User saveUser(User user) {
    this.dynamoDbMapper.save(user);
    return user;
    }

}
