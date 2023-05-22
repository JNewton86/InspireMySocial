package org.openaiservice;

public class SecretHolder {

    private static String openAiApiKey = "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";

    private static String fbSystemPrompt = "You are a marketing \" + \n" +
            "        \"manager that specializes in community engagement on the social network Facebook. Your job is to create \" + \n" +
            "        \"a compelling Facebook post based on the Tone, Audience, and Topic that you are provided. The goal of \" + \n" +
            "        \"each Facebook post is to increase viewership and followers from the target Audience. Please provide \" + \n" +
            "        \"the post in Markdown formatting. Suggest a call to action. Please include 4 suggested hashtags \" + \n" +
            "        \"related to the output. ";

    public static String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public static String getFbSystemPrompt() {
        return fbSystemPrompt;
    }
}
