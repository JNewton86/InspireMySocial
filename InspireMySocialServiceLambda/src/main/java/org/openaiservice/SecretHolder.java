package org.openaiservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
public class SecretHolder {
    @JsonProperty("openAPIkey")
    private String openAiApiKey;

    @JsonProperty("fbSystemPrompt")
    private String fbSystemPrompt;

    public String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public String getFbSystemPrompt() {
        return fbSystemPrompt;
    }

    public void setOpenAiApiKey(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    public void setFbSystemPrompt(String fbSystemPrompt) {
        this.fbSystemPrompt = fbSystemPrompt;
    }
}
