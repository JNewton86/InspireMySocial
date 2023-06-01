package org.Converter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretConverter {

    @JsonProperty("OpenAiAPIKey")
    private String openAiAPIKey;

    @JsonProperty("fbPrompt")
    private String fbPrompt;

    public String getOpenAiAPIKey() {
        return openAiAPIKey;
    }

    public void setOpenAiAPIKey(String openAiAPIKey) {
        this.openAiAPIKey = openAiAPIKey;
    }

    public String getFbPrompt() {
        return fbPrompt;
    }

    public void setFbPrompt(String fbPrompt) {
        this.fbPrompt = fbPrompt;
    }
}
