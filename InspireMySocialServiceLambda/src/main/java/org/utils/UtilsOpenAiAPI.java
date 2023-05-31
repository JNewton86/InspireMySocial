package org.utils;

import org.Converter.SecretConverter;
import org.openaiservice.SecretHolder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilsOpenAiAPI {

    private static SecretHolder secretHolder;
    /**
     * @param secretName
     * @return
     * @throws ResourceNotFoundException
     * @throws InvalidParameterException
     * @throws InvalidRequestException
     */
    public static String getSecret()
            throws ResourceNotFoundException, InvalidParameterException, InvalidRequestException{

        String secretName = "openAPiSecrets";
        Region region = Region.of("us-east-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;
        getSecretValueResponse = client.getSecretValue(getSecretValueRequest);

        return getSecretValueResponse.secretString();
    }

    public static SecretHolder sortSecret() throws JsonProcessingException {
        if (secretHolder == null) {
            String results = UtilsOpenAiAPI.getSecret();
            ObjectMapper MAPPER = new ObjectMapper();
            secretHolder = MAPPER.readValue(results, SecretHolder.class);
        }
        return secretHolder;
    }
}


