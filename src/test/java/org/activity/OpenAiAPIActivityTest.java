package org.activity;

import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.activity.request.CreateContentRequest;
import org.junit.jupiter.api.Test;
import org.metrics.MetricsPublisher;
import org.openaiservice.OpenAiDao;
import org.openaiservice.OpenAiService;
import org.openaiservice.SecretHolder;

import java.sql.SQLOutput;

class OpenAiAPIActivityTest {

    @Test
    public void getContent_valid_success(){

        OpenAiService openAiService = new OpenAiService(SecretHolder.getOpenAiApiKey());
        OpenAiDao openAiDao = new OpenAiDao(openAiService,null );
        CreateContentRequest createContentRequest = new CreateContentRequest("Jeff", "Face Book Post",
                "condescending", "software engineers", "hire me please, I'm cool", 200 );
//        ChatCompletionResult result =openAiDao.createContent(createContentRequest);
//        System.out.println(result.getChoices().get(0).toString());
        String result = openAiDao.createContent(createContentRequest);
        System.out.println(result.toString());

    }


}