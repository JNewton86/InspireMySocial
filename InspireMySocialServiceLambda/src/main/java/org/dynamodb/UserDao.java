package org.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.dynamodb.models.User;
import org.exception.UserNotFoundException;
import org.metrics.MetricsPublisher;

import javax.inject.Inject;

public class UserDao {

    private final DynamoDBMapper dynamoDbMapper;

    private final MetricsPublisher metricsPublisher;

    /**
     * Constructor used by dagger for dependency injection.
     * @param dynamoDbMapper singleton of the mapper provided by dagger
     * @param metricsPublisher singleton of the metricPublisher provided by dagger
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Method used to load user from DynamoDB user table.
     * @param userEmail email is used as userId and is a String
     * @return passes back the user object that was retrieved from DynamoDb table
     */
    public User getUser(String userEmail) {
        User user = this.dynamoDbMapper.load(User.class, userEmail);

        if (user == null) {
            throw new UserNotFoundException("Could not find the user with userEmail: " + userEmail);
        }
        return user;
    }

    /**
     * Method used to save user to the DynamoDB user table.
     * @param user object that is saved to the table
     * @return passes back the saved object.
     */
    public User saveUser(User user) {
        this.dynamoDbMapper.save(user);
        return user;
    }

}
