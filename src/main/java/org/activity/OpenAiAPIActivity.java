package org.activity;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import org.openaiservice.SecretHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenAiAPIActivity {

    private final OpenAiService openAiService;
    private final String token = SecretHolder.getOpenAiApiKey();



    private StringBuilder aiResponseString = new StringBuilder();

    public OpenAiAPIActivity() {
        this.openAiService = new OpenAiService(token);
    }

    public String getContent() {
        System.out.println("Streaming chat completion...");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), SecretHolder.getFbSystemPrompt());
        messages.add(systemMessage);
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), "Please write a Facebook post about " +
                "the keywords Scuba Lessons. The audience of the post is the general public. " +
                "Please use a tone of conversational for the facebook post. The post length should be no " +
                "more than 150 words.");
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(500)
                .temperature(0.9)
                .logitBias(new HashMap<>())
                .build();


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
        return aiResponseString.toString();
    }

    public StringBuilder getAiResponseString() {
        return aiResponseString;
    }
}

