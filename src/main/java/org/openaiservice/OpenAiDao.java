package org.openaiservice;

import com.theokanning.openai.completion.chat.*;
import org.activity.request.CreateContentRequest;
import org.metrics.MetricsPublisher;
import org.model.ChatCompletion;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenAiDao {

    private final OpenAiService openAiService;
    private final String token = SecretHolder.getOpenAiApiKey();
    private final MetricsPublisher metricsPublisher;

    @Inject
    public OpenAiDao(org.openaiservice.OpenAiService openAiService, MetricsPublisher metricsPublisher){
        this.openAiService = openAiService;
        this.metricsPublisher = metricsPublisher;
    }

    public OpenAiDao(OpenAiService openAiService){
        this.openAiService = openAiService;
        this.metricsPublisher = null;
    }

    public String createContent(CreateContentRequest createContentRequest) {
        StringBuilder aiResponseString = new StringBuilder();
        System.out.println("Streaming chat completion...");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), SecretHolder.getFbSystemPrompt());
        messages.add(systemMessage);
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a "
                + createContentRequest.getContentType() + "about the keywords " + createContentRequest.getTopic() +
                ". The audience of the post is " + createContentRequest.getAudience() + "Please use a tone of " +
                createContentRequest.getTone() + "for the " + createContentRequest.getContentType()+ ". The post length " +
                "should be no more than " + createContentRequest.getWordCount() + " words.");
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


//        ChatCompletionResult chatCompletion = openAiService.createChatCompletion(chatCompletionRequest);
                openAiService.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(chatCompletionChunk -> {
                    for (ChatCompletionChoice chatCompletionChoice : chatCompletionChunk.getChoices()) {
                        if(chatCompletionChoice.getMessage().getContent() == null){
                            chatCompletionChoice.getMessage().setContent("#");
                        }
                        aiResponseString.append(chatCompletionChoice.getMessage().getContent());
                        System.out.print(chatCompletionChoice.getMessage().getContent());
                    }

                });

        openAiService.shutdownExecutor();
//        return chatCompletion;
        return aiResponseString.toString();
    }


}


