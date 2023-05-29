package org.Converter;

import org.dynamodb.models.Content;
import org.junit.jupiter.api.Test;
import org.model.ContentModel;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toContentModel_withValidContent_convertsContent() {
        Content content = new Content();
        content.setUserID("The Dude");
        content.setContentId("12345");
        content.setContentType("fbpost");
        content.setTone("Humerous");
        content.setTopic("Dogs are great");
        content.setAudience("Cat lovers");
        content.setWordCount(250);
        content.setDeleted(false);
        content.setAiMessage("Cats are not as great a companion as dogs, dogs love you.");
        content.setPromptTokens(150);
        content.setCompletionTokens(200);
        content.setTotalTokens(350);

        ContentModel contentModel = modelConverter.toContentModel(content);
        System.out.println("Content Model was created as : " +contentModel.toString());
        assertEquals(content.getUserID(),contentModel.getUserId());
        assertEquals(content.getContentId(), contentModel.getContentId());
        assertEquals(content.getContentType(), contentModel.getContentType());
        assertEquals(content.getTopic(), contentModel.getTopic());
        assertEquals(content.getAiMessage(), contentModel.getAiMessage());
        assertEquals(content.getDeleted(), contentModel.getDeleted());

    }
}