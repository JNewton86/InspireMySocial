package org.dynamodb;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.dynamodb.models.Content;
import org.exception.ContentNotFoundException;
import org.metrics.MetricsConstants;
import org.metrics.MetricsPublisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;



@Singleton
public class ContentDao {

    private final DynamoDBMapper dynamoDbMapper;

    private final MetricsPublisher metricsPublisher;

    @Inject
    public ContentDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Saves (creates or updates) the given content.
     *
     * @param content The content to save
     * @return The Content object that was saved
     */
    public Content saveContent(Content content) {
        this.dynamoDbMapper.save(content);
        return content;
    }

    public Content getContent(String id, String contentId) {
        Content playlist = this.dynamoDbMapper.load(Content.class, id, contentId);

        if (playlist == null) {
            metricsPublisher.addCount(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT, 1);
            throw new ContentNotFoundException("Could not find content with id " + id);
        }
        metricsPublisher.addCount(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT, 0);
        return playlist;
    }

    public List<Content> getAllContentForUser(String userId){
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userEmail", new AttributeValue().withS(String.valueOf(userId)));
          DynamoDBQueryExpression<Content> queryExpression = new DynamoDBQueryExpression<Content>()
                .withConsistentRead(false)
                .withKeyConditionExpression("userEmail = :userEmail")
                .withExpressionAttributeValues(valueMap);

          List<Content> allResults = mapper.query(Content.class, queryExpression);
          List<Content> filteredResults = new ArrayList<>();
          for(Content c : allResults){
              if (!c.getDeleted()){
                  filteredResults.add(c);
              }
          }
          return filteredResults;
    }
}
