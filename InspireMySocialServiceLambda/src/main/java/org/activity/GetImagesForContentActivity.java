package org.activity;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import software.amazon.awssdk.services.s3.S3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.util.IOUtils;
import org.activity.request.GetImagesForContentRequest;
import org.activity.result.GetImagesForContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.UserDao;
import org.exception.ContentNotFoundException;
import org.model.ImageModel;
import org.openaiservice.OpenAiDao;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;


import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.util.List;

public class GetImagesForContentActivity {

    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiDao openAiDao;
    private final UserDao userDao;

    @Inject
    public GetImagesForContentActivity(ContentDao contentDao, OpenAiDao openAiDao, UserDao userDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;
        this.userDao = userDao;
    }

    public GetImagesForContentResult handleRequest(final GetImagesForContentRequest getImagesForContentRequest){
        try {
            log.info("Recieved GetImageForContentRequest: {}", getImagesForContentRequest);
            String userEmail = getImagesForContentRequest.getUserEmail();
            String contentId = getImagesForContentRequest.getContentId();
            List<String> imageNames = contentDao.getContent(userEmail, contentId).getImages();
            if(imageNames == null){
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
                    .withData(base64Images).build());

            return getImagesForContentResult;

        } catch(Exception e){
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
