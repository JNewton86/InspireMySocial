package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateContentRequest.Builder.class)
public class CreateContentRequest {

    private String userId;
    private String contentType;
    private String tone;
    private String audience;
    private String topic;
    private Integer wordCount;

    public CreateContentRequest(String userId, String contentType, String tone,
                                String audience, String topic, Integer wordCount) {
        this.userId = userId;
        this.contentType = contentType;
        this.tone = tone;
        this.audience = audience;
        this.topic = topic;
        this.wordCount = wordCount;
    }

    public CreateContentRequest(){};
    public String getUserId() {
        return userId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getTone() {
        return tone;
    }

    public String getAudience() {
        return audience;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();}

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String contentType;
        private String tone;
        private String audience;
        private String topic;
        private Integer wordCount;

        public Builder withUserId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder withContentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public Builder withTone(String tone){
            this.tone = tone;
            return this;
        }

        public Builder withAudience(String audience){
            this.audience = audience;
            return this;
        }
        public Builder withTopic(String topic){
            this.topic = topic;
            return this;
        }
        public Builder withWordCount(Integer wordCount){
            this.wordCount = wordCount;
            return this;
        }

        public CreateContentRequest build() { return new CreateContentRequest(userId, contentType, audience,
                tone, topic, wordCount); }

    }

    @Override
    public String toString() {
        return "CreateContentRequest{" +
                "userId='" + userId + '\'' +
                ", contentType='" + contentType + '\'' +
                ", tone='" + tone + '\'' +
                ", audience='" + audience + '\'' +
                ", topic='" + topic + '\'' +
                ", wordCount=" + wordCount +
                '}';
    }
}

