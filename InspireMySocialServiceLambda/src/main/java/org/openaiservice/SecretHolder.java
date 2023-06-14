package org.openaiservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretHolder {
    @JsonProperty("openAPIkey")
    private String openAiApiKey;

    @JsonProperty("fbSystemPrompt")
    private String fbSystemPrompt;

    @JsonProperty("instaSystemPrompt")
    private String instaSystemPrompt;

    @JsonProperty("twitterSystemPrompt")
    private String twitterSystemPrompt;

    @JsonProperty("ytLongSystemPrompt")
    private String ytLongSystemPrompt;

    @JsonProperty("ytShortSystemPrompt")
    private String ytShortSystemPrompt;

    @JsonProperty("linkedInSystemPrompt")
    private String linkedInSystemPrompt;
    public String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public String getFbSystemPrompt() {
        return fbSystemPrompt;
    }

    public String getInstaSystemPrompt() {
        return instaSystemPrompt;
    }

    public String getTwitterSystemPrompt() {
        return twitterSystemPrompt;
    }

    public String getYtLongSystemPrompt() {
        return ytLongSystemPrompt;
    }

    public String getYtShortSystemPrompt() {
        return ytShortSystemPrompt;
    }

    public String getLinkedInSystemPrompt() {
        return linkedInSystemPrompt;
    }

    public void setInstaSystemPrompt(String instaSystemPrompt) {
        this.instaSystemPrompt = instaSystemPrompt;
    }

    public void setOpenAiApiKey(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    public void setFbSystemPrompt(String fbSystemPrompt) {
        this.fbSystemPrompt = fbSystemPrompt;
    }

    public void setTwitterSystemPrompt(String twitterSystemPrompt) {
        this.twitterSystemPrompt = twitterSystemPrompt;
    }

    public void setYtLongSystemPrompt(String ytLongSystemPrompt) {
        this.ytLongSystemPrompt = ytLongSystemPrompt;
    }

    public void setYtShortSystemPrompt(String ytShortSystemPrompt) {
        this.ytShortSystemPrompt = ytShortSystemPrompt;
    }

    public void setLinkedInSystemPrompt(String linkedInSystemPrompt) {
        this.linkedInSystemPrompt = linkedInSystemPrompt;
    }
}
