package org.openaiservice;

public class SecretHolder {

    private static String openAiApiKey = "token";

    private static String fbSystemPrompt = "prompt";

    public static String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public static String getFbSystemPrompt() {
        return fbSystemPrompt;
    }
}