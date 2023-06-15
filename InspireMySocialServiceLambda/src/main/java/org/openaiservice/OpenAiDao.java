package org.openaiservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theokanning.openai.completion.chat.*;
import org.activity.request.CreateContentRequest;
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
                    createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                    ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                    createContentRequest.getTone() + "for the " + createContentRequest.getContentType() + ". T" +
                    "he post length should be no more than " + createContentRequest.getWordCount() + " words long.");
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

    //TODO Add logic to check for userId in table already, else add.
    //TODO The below method is commented out for the time being as it's being replcaed by a different call. Leaving here
    //TODO until the new method is tested and satiscactory
//    public ChatCompletionResult createContent(CreateContentRequest createContentRequest) {
//
//        //StringBuilder for the streaming output from API call
//        StringBuilder aiResponseStringBuilder = new StringBuilder();
//        System.out.println("Streaming chat completion...");
//
//        //Creating and loading the initial ChatMessage List as needed by API call.
//        final List<ChatMessage> messages = new ArrayList<>();
//
//        // System Prompt
//        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
//                SecretHolder.getFbSystemPrompt());
//        messages.add(systemMessage);
//
//        // Specific Prompt from User with User variables
//        //TODO Switch for types of content creation
//        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a " +
//                 createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
//                ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
//                createContentRequest.getTone() + "for the " + createContentRequest.getContentType() +
//                ". The post length should be no more than " + createContentRequest.getWordCount() + " words.");
//        messages.add(userMessage);
//
//        //Building the chatCompletionRequest
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
//                .builder()
//                .model("gpt-4-0314")
//                .messages(messages)
//                .n(1)
//                .maxTokens(500)
//                .temperature(0.9)
//                .logitBias(new HashMap<>())
//                .build();

    //The below steamChatCompletion Method streams a series of response chunks
//        ChatCompletionResult chatCompletionResult = new ChatCompletionResult();
//        openAiService.streamChatCompletion(chatCompletionRequest)
//                .doOnError(Throwable::printStackTrace)
//                .blockingForEach(chatCompletionChunk -> {
//                    //Setting the result contentID
//                    chatCompletionResult.setId(chatCompletionChunk.getId());
//                    //Setting the result object type
//                    chatCompletionResult.setObject(chatCompletionChunk.getObject());
//                    //Setting the result model type for tracking
//                    chatCompletionResult.setModel(chatCompletionChunk.getModel());
//                    // creating usage tracking from chunks
//                    Usage usage = new Usage();
//                    //ToDo Implement token tracking from checkCompletionChunk
//                    //Below are place holders as I review logic behind tokens and chunks.
//                    usage.setPromptTokens(1);
//                    usage.setCompletionTokens(1);
//                    usage.setTotalTokens(2);
//
//                    //loop to compile string output message.
//                    for (ChatCompletionChoice chatCompletionChoice : chatCompletionChunk.getChoices()) {
//                        if (chatCompletionChoice.getMessage().getContent() == null) {
//                            chatCompletionChoice.getMessage().setContent("#");
//                        }
//                        aiResponseStringBuilder.append(chatCompletionChoice.getMessage().getContent());
//                        System.out.print(chatCompletionChoice.getMessage().getContent());
//                    }
//                });
//        //Manually Building the components of the ChatCompletionResult from fragments
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setRole("assistant");
//        chatMessage.setContent(aiResponseStringBuilder.toString());
//        ChatCompletionChoice chatCompletionChoice = new ChatCompletionChoice();
//        chatCompletionChoice.setIndex(0);
//        chatCompletionChoice.setMessage(chatMessage);
//        List<ChatCompletionChoice> choices = new ArrayList<>();
//        choices.add(chatCompletionChoice);
//        chatCompletionResult.setChoices(choices);
//        openAiService.shutdownExecutor();
//        return chatCompletionResult;
//    }
}
