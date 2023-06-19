package org.activity;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;
import org.activity.request.CreateImageForContentRequest;
import org.activity.result.CreateImageForContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamodb.ContentDao;
import org.dynamodb.UserDao;
import org.dynamodb.models.Content;
import org.dynamodb.models.User;
import org.exception.ContentNotFoundException;
import org.exception.CreateImageForContentException;
import org.model.ImageModel;
import org.openaiservice.OpenAiDao;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import javax.imageio.ImageIO;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CreateImageForContentActivity {
    private final Logger log = LogManager.getLogger();
    private final ContentDao contentDao;
    private final OpenAiDao openAiDao;
    private final UserDao userDao;

    // Interum Credit cost for create image
    Integer costOfImage = 2;

    @Inject
    public CreateImageForContentActivity(ContentDao contentDao, OpenAiDao openAiDao, UserDao userDao) {
        this.contentDao = contentDao;
        this.openAiDao = openAiDao;
        this.userDao = userDao;
    }

    public CreateImageForContentResult handleRequest(final CreateImageForContentRequest createImageForContentRequest) {

    try{
        //retrieve the content that was created for this contentId, so we have access to data
        Content content = contentDao.getContent(createImageForContentRequest.getUserId(), createImageForContentRequest.getContentId());

        // create a new request object and populate it
        CreateImageRequest createImageRequest = new CreateImageRequest();
        createImageRequest.setPrompt("Please create an image that can be used with this social media post and make it something that" +
                "will encourage users to click on my post the post was about the topic of my post." + content.getTopic());
        createImageRequest.setN(createImageForContentRequest.getNumberOfImages());
        createImageRequest.setSize(createImageForContentRequest.getImageSize());
        createImageRequest.setN(createImageForContentRequest.getNumberOfImages());
        createImageRequest.setResponseFormat(createImageForContentRequest.getResponse_format());

        //access openAiDao to generate the image
        ImageResult imageResult = openAiDao.createImageForContent(createImageRequest);

        //convert the base64JSON "image" to png for each of the images created. save name to content object

        List<String> imageStrings = content.getImages();
        if (content.getImages().isEmpty() || content.getImages() == null) {
             imageStrings = new ArrayList<>();
        }
        for(int i = 0; i<imageResult.getData().size(); i++) {
            String base64JsonString = imageResult.getData().get(0).getB64Json();
            String bucketName = "ims-image-content";
            String objectKey = "image"+i+createImageForContentRequest.getContentId()+".png";
            saveBase64JsonToS3(base64JsonString, bucketName, objectKey);
            imageStrings.add(objectKey);
        }
        //Set the list of file names to the images value for this content & save to Dynamo
        content.setImages(imageStrings);
        contentDao.saveContent(content);

        // Subtract cost of image in credits from user's balance
        User user = userDao.getUser(createImageForContentRequest.getUserId());
        user.setCreditBalance(user.getCreditBalance()-costOfImage);
        userDao.saveUser(user);

        //Create the imageModel return.
        CreateImageForContentResult createImageForContentResult = new CreateImageForContentResult(ImageModel.builder()
                .withData(imageStrings).build());
        return createImageForContentResult;

        //Catch block for errors both I/O and otherwise, log it, and throw it with message pointing to class w/ issue
        } catch (Exception e) {
            log.error(e);
            throw new CreateImageForContentException("error thrown creating image" + e);
        }
    }


    private static void saveBase64JsonToS3(String base64JsonString, String bucketName, String objectKey) throws IOException {
        // Convert base64 string to byte array
        byte[] imgBytes = Base64.getDecoder().decode(base64JsonString);

        // Deserialize byte array into a BufferedImage
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imgBytes));

        // Convert BufferedImage to a ByteArrayOutputStream in PNG format, to prepare for upload to S3
        ByteArrayOutputStream outputImageStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", outputImageStream);
        RequestBody requestBody = RequestBody.fromByteBuffer(ByteBuffer.wrap(outputImageStream.toByteArray()));

        // Initialize AWS S3 client
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_2)
                .build();

        // Save the image to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);
    }
}

