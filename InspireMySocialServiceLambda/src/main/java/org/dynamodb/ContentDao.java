package org.dynamodb;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.dynamodb.models.Content;
import org.exception.ContentNotFoundException;
import org.metrics.MetricsConstants;
import org.metrics.MetricsPublisher;

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

    public Content getContent(String id) {
        Content playlist = this.dynamoDbMapper.load(Content.class, id);

        if (playlist == null) {
            metricsPublisher.addCount(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT, 1);
            throw new ContentNotFoundException("Could not find content with id " + id);
        }
        metricsPublisher.addCount(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT, 0);
        return playlist;
    }
}
