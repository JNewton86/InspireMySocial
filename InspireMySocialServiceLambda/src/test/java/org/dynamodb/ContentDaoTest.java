package org.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.dynamodb.models.Content;
import org.exception.ContentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.metrics.MetricsConstants;
import org.metrics.MetricsPublisher;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ContentDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;

    private ContentDao contentDao;

    @BeforeEach
    public void setup(){
        initMocks(this);
       contentDao = new ContentDao(dynamoDBMapper,metricsPublisher);
    }

    @Test
    public void savePlaylist_callsMapperWithPlaylist() {
        // GIVEN
        Content content = new Content();

        // WHEN
        Content result = contentDao.saveContent(content);

        // THEN
        verify(dynamoDBMapper).save(content);
        assertEquals(content, result);
    }

    @Test
    public void getContent_validCall_callsMapperAndMetricsPublisher(){
        // GIVEN
        String contentId = "1234";
        when(dynamoDBMapper.load(Content.class, contentId)).thenReturn(new Content());

        // WHEN
        Content content = contentDao.getContent(contentId);

        // THEN
        assertNotNull(content);
        verify(dynamoDBMapper).load(Content.class, contentId);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void getContent_contentIdNotFound_throwsContentNotFoundException() {
        // GIVEN
        String nonexistentContentId = "NotReal";
        when(dynamoDBMapper.load(Content.class, nonexistentContentId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ContentNotFoundException.class, () -> contentDao.getContent(nonexistentContentId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETCONTENT_CONTENTNOTFOUND_COUNT), anyDouble());
    }
}