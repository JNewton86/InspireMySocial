package org.openaiservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import org.activity.request.CreateContentRequest;
import org.activity.request.CreateImageForContentRequest;
import org.metrics.MetricsPublisher;
import org.utils.UtilsOpenAiAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

public class OpenAiDao {

    private final OpenAiService openAiService;

    private final MetricsPublisher metricsPublisher;

    /**
     * Constructor for OpenAiDao.
     *
     * @param openAiService    accepets an openAiService injected from Dagger
     * @param metricsPublisher MetricsPublisher also from Dagger
     */
    @Inject
    public OpenAiDao(OpenAiService openAiService, MetricsPublisher metricsPublisher) {
        this.openAiService = openAiService;
        this.metricsPublisher = metricsPublisher;


    }

    /**
     * Accepts a request, creates a ChatMessage object, passes in system prompt, and user prompt with chatCompletion
     * request.
     *
     * @param createContentRequest request from API call
     * @return currently returns a string after a chatstream is complete, need to refactor this to returnChatCompletion
     */

    public ChatCompletionResult createContent(CreateContentRequest createContentRequest) {
        System.out.println("Streaming chat completion...");
        SecretHolder secretHolder = null;
        try {
            secretHolder = UtilsOpenAiAPI.sortSecret();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final List<ChatMessage> messages = new ArrayList<>();
        if(Objects.equals(createContentRequest.getContentType(), "Face Book Post")) {
            System.out.println("Streaming FB chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getFbSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than " + createContentRequest.getWordCount() + " words.");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(500)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
       }
        if(Objects.equals(createContentRequest.getContentType(), "Instagram Post")) {
            System.out.println("Streaming Insta chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getInstaSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than " + createContentRequest.getWordCount() + " characters long.");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(500)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
        }
        if(Objects.equals(createContentRequest.getContentType(), "Twitter Post")) {
            System.out.println("Streaming Twitter chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getTwitterSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than 280 characters long.");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(250)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
        }
        if(Objects.equals(createContentRequest.getContentType(), "YouTube Short Script")) {
            System.out.println("Streaming YT Short chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getYtShortSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "and Provide a catchy saying around the topic " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() +
                    "Once you have provided the catchy saying please provide a click-worthy title, YoutubeShort video " +
                    "description, and three hashtags related to the topic that will increase the audience");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(250)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
        }
        if(Objects.equals(createContentRequest.getContentType(), "YouTube Long Script")) {
            System.out.println("Streaming YT Long chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getYtLongSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than " + createContentRequest.getWordCount() + " minutes long.");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(2500)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
        }
        if(Objects.equals(createContentRequest.getContentType(), "LinkedIn Post")) {
            System.out.println("Streaming LinkedIn chat completion...");
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    secretHolder.getLinkedInSystemPrompt());
            messages.add(systemMessage);
            final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
                    createContentRequest.getContentType() + "about the" + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than " + createContentRequest.getWordCount() + " words long.");
            messages.add(userMessage);
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-4-0314")
                    .messages(messages)
                    .n(1)
                    .maxTokens(1250)
                    .temperature(0.9)
                    .logitBias(new HashMap<>())
                    .build();
            ChatCompletionResult chatCompletionResult = openAiService.createChatCompletion(chatCompletionRequest);
            return chatCompletionResult;
        }
        return null;
    }

    public ImageResult createImageForContent(CreateImageRequest createImageRequest){
        System.out.println("Streaming Image completion...");
        SecretHolder secretHolder = null;
        try {
            secretHolder = UtilsOpenAiAPI.sortSecret();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ImageResult imageResult = openAiService.createImage(createImageRequest);

        return imageResult;
    }
}
