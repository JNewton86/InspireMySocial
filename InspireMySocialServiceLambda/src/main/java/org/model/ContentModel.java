package org.model;

import java.util.Objects;

public class ContentModel {

    private final String userId;
    private final String contentId;
    private final String contentType;
    private final String topic;
    private final String aiMessage;
    private final Boolean isDeleted;

    public ContentModel(String userId, String contentId, String contentType, String topic,
                        String aiMessage, Boolean isDeleted) {
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
        this.topic = topic;
        this.aiMessage = aiMessage;
        this.isDeleted = isDeleted;
    }

    public String getUserId() {
        return userId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getTopic() {
        return topic;
    }

    public String getAiMessage() {
        return aiMessage;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public String getContentId() {
        return contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentModel that = (ContentModel) o;
        return Objects.equals(userId, that.userId) && Objects.equals(contentId, that.contentId) &&
                Objects.equals(contentType, that.contentType) && Objects.equals(topic, that.topic) &&
                Objects.equals(aiMessage, that.aiMessage) && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, contentId, contentType, topic, aiMessage, isDeleted);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder{
        private String userId;
        private String contentId;
        private String contentType;
        private String topic;
        private String aiMessage;
        private Boolean isDeleted;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withContentId(String contentId) {
            this.contentId = contentId;
            return this;
        }
        public Builder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }
        public Builder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder withAiMessage(String aiMessage) {
            this.aiMessage = aiMessage;
            return this;
        }
        public Builder withIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public ContentModel build(){return  new ContentModel(userId, contentId,  contentType, topic, aiMessage, isDeleted);}
    }
}
