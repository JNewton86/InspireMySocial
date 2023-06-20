package org.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretConverterTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_correctlySerializeAndDeserialize() throws JsonProcessingException {
        String openAiAPIKey = "sample-api-key";
        String fbPrompt = "sample-fb-prompt";

        SecretConverter secretConverter = new SecretConverter();
        secretConverter.setOpenAiAPIKey(openAiAPIKey);
        secretConverter.setFbPrompt(fbPrompt);

        // Serialize the SecretConverter object to JSON
        String jsonString = objectMapper.writeValueAsString(secretConverter);

        // Deserialize the JSON string back to a SecretConverter object
        SecretConverter deserializedSecretConverter = objectMapper.readValue(jsonString, SecretConverter.class);

        // Compare the original and deserialized SecretConverter objects
        assertEquals(openAiAPIKey, deserializedSecretConverter.getOpenAiAPIKey());
        assertEquals(fbPrompt, deserializedSecretConverter.getFbPrompt());
    }
}