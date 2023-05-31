package org.Converter;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
