package org.activity;
import com.theokanning.openai.service.OpenAiService;
import org.activity.request.OpenAiChatRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openaiservice.OpenAiDao;
import org.openaiservice.SecretHolder;

import javax.inject.Inject;

public class OpenAiAPIActivity {

    private final OpenAiDao openAiDao;
    private final Logger log = LogManager.getLogger();

    @Inject
    public OpenAiAPIActivity(OpenAiDao openAiDao) {
        this.openAiDao = openAiDao;
    }

    public OpenAiChatResult handleRequest(final OpenAiChatRequest openAiChatRequest){

    }
}

