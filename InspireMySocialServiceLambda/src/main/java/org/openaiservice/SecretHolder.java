package org.openaiservice;

import org.utils.UtilsOpenAiAPI;

public class SecretHolder {

    static String InspireMySocialServiceLambdaSecret;

    private static String openAiApiKey = UtilsOpenAiAPI.getSecret(InspireMySocialServiceLambdaSecret).;

    private static String fbSystemPrompt = "system_prompt";

    public static String getOpenAiApiKey() {
        return InspireMySocialServiceLambdaSecret.openAiApiKey;
    }

    public static String getFbSystemPrompt() {
        return fbSystemPrompt;
    }
}
