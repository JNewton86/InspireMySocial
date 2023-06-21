package org.activity;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.activity.request.GetImagesForContentRequest;
import org.activity.result.GetImagesForContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.UserDao;
import org.exception.ContentNotFoundException;
import org.model.ImageModel;
import org.openaiservice.OpenAiDao;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;


import java.net.URL;
import java.util.*;

import java.util.List;
import javax.inject.Inject;

public class GetImagesForContentActivity {

    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiDao openAiDao;
    private final UserDao userDao;

    /**
     * Constructor used by Dagger to instantiate using dependency injection.
     * @param contentDao passed in by dagger, DAO object to access content DynamoDB table
     * @param openAiDao passed in by dagger, DAO object used to make OpenAI API Call
     * @param userDao passed in by dagger, DAO object to access user DynamoDB table
     */
    @Inject
    public GetImagesForContentActivity(ContentDao contentDao, OpenAiDao openAiDao, UserDao userDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;
        this.userDao = userDao;
    }

    /**
     * Method to process the request to retrieve all images for a specific content, called by the relevant Lambda.
     * @param getImagesForContentRequest object created by the lambda from the API call that was received
     * @return returns an object that contains a list of string that are URLs to the images stored in S3
     */
    public GetImagesForContentResult handleRequest(final GetImagesForContentRequest getImagesForContentRequest) {
        try {
            log.info("Recieved GetImageForContentRequest: {}", getImagesForContentRequest);
            String userEmail = getImagesForContentRequest.getUserEmail();
            String contentId = getImagesForContentRequest.getContentId();
            List<String> imageNames = contentDao.getContent(userEmail, contentId).getImages();
            if (imageNames == null) {
                imageNames = new ArrayList<>();
            }
            String bucketName = "ims-image-content";
            List<String> base64Images = new ArrayList<>();
            Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);
            AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                    .withRegion(com.amazonaws.regions.Regions.US_EAST_2)
                    .build();
            List<String> imageUrls = new ArrayList<>();
            for (String name : imageNames) {
                GeneratePresignedUrlRequest generatredPresignedUrlRequest =
                            new GeneratePresignedUrlRequest(bucketName, name)
                                    .withMethod(HttpMethod.GET)
                                    .withExpiration(expiration);

                URL url = s3client.generatePresignedUrl(generatredPresignedUrlRequest);
                imageUrls.add(url.toString());

            }
            GetImagesForContentResult getImagesForContentResult = new GetImagesForContentResult(ImageModel.builder()
                    .withData(imageUrls).build());

            return getImagesForContentResult;

        } catch (Exception e) {
            log.error("error thrown when retrieving images from s3", e);
            throw new ContentNotFoundException();
        }
    }


    private static byte[] getImageDataFromS3(String bucketName, String objectKey) {
        Region s3Region = Region.of("us-east-2");

        S3Client s3Client = S3Client.builder()
                .region(s3Region)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObject(getObjectRequest,
                ResponseTransformer.toBytes());

        return responseBytes.asByteArray();
    }
}
