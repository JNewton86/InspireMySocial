package org.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateImageForContentRequest.Builder.class)
public class CreateImageForContentRequest {

    private String userId;

    private String contentId;

    private String prompt;
    private Integer numberOfImages;
    private String imageSize;
    private String response_format;

    public CreateImageForContentRequest(String userId, String contentId, String prompt, Integer numberOfImages, String imageSize, String response_format) {
        this.userId = userId;
        this.contentId = contentId;
        this.prompt = prompt;
        this.numberOfImages = numberOfImages;
        this.imageSize = imageSize;
        this.response_format = response_format;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Integer getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(Integer numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getResponse_format() {
        return response_format;
    }

    public void setResponse_format(String response_format) {
        this.response_format = response_format;
    }

    public static Builder builder() {
        return new Builder();}

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        private String contentId;
        private String prompt;
        private Integer numberOfImages;
        private String imageSize;
        private String response_format;

        public Builder withUserId(String userId){
            this.userId= userId;
            return this;
        }

        public Builder withContentId(String contentId){
            this.contentId= contentId;
            return this;
        }
        public Builder withPrompt(String prompt){
            this.prompt= prompt;
            return this;
        }

        public Builder withNumberOfImages(Integer numberOfImages){
            this.numberOfImages = numberOfImages;
            return this;
        }

        public Builder withImageSize(String imageSize){
            this.imageSize= imageSize;
            return this;
        }

        public Builder withResponse_format(String response_format){
            this.response_format= response_format;
            return this;
        }

        public CreateImageForContentRequest build() { return new CreateImageForContentRequest(userId, contentId, prompt, numberOfImages,
                imageSize,response_format);
        }
    }
}
