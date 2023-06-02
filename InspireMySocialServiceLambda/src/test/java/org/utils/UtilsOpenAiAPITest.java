package org.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.Converter.SecretConverter;
import org.junit.jupiter.api.Test;
import org.openaiservice.SecretHolder;

import static org.junit.jupiter.api.Assertions.*;

class UtilsOpenAiAPITest {

    String secret = "openAPiSecrets";

    //Integration Test, commented out as requires aws sso to use.
//    @Test
//    void testMethod() throws JsonProcessingException {
//        SecretHolder secretHolder = UtilsOpenAiAPI.sortSecret();
//        System.out.println("API Key: " + secretHolder.getOpenAiApiKey());
//        System.out.println("FB Prompt: " + secretHolder.getFbSystemPrompt());
//    }

}