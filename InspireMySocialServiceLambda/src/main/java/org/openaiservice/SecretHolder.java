package org.openaiservice;

public class SecretHolder {

    private static String openAiApiKey = "API_TOKEN";

    private static String fbSystemPrompt = "System Prompt";

    public static String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public static String getFbSystemPrompt() {
        return fbSystemPrompt;
    }
}
