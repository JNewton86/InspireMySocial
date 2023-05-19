package org.activity;

import org.junit.jupiter.api.Test;
import org.openaiservice.OpenAiService;
import org.openaiservice.SecretHolder;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiAPIActivityTest {

    @Test
    public void getContent_valid_success(){

        OpenAiService openAiService = new OpenAiService(SecretHolder.getOpenAiApiKey());
        OpenAiAPIActivity activity = new OpenAiAPIActivity();
        activity.getContent();
        System.out.print(activity);
    }


}