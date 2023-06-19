package org.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openaiservice.SecretHolder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.*;

public class UtilsOpenAiAPI {

    private static SecretHolder secretHolder;

    /** a Method for retrieving the secret from AWS Secret Manager.
     * @return returns a JSON string of secrets
     * @throws ResourceNotFoundException thrown when resource not found.
     * @throws InvalidParameterException throw when the parameter is invalid.
     * @throws InvalidRequestException thrown when the request is not valid.
     */
    public static String getSecret()
            throws ResourceNotFoundException, InvalidParameterException, InvalidRequestException {

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

    /**
     * a Method for retrieving secrets from AWS Secret Manager, checking if current secretHolder is empty, and
     * deserializing the JSON to the individual components.
     * @return an instance of secretHolder with the appropriate secrets stored in the class parameters.
     * @throws JsonProcessingException thrown when the JSON string can't be deserialized.
     */
    public static SecretHolder sortSecret() throws JsonProcessingException {
        if (secretHolder == null) {
            String results = UtilsOpenAiAPI.getSecret();
            ObjectMapper MAPPER = new ObjectMapper();
            secretHolder = MAPPER.readValue(results, SecretHolder.class);
        }
        return secretHolder;
    }
}


