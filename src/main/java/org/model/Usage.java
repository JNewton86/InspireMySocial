package org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usage {

    @JsonProperty("prompt_tokens")
    private int promptTokens;

    @JsonProperty("completion_tokens")
    private int completionTokens;

    @JsonProperty("total_tokens")
    private int totalTokens;

    // Getters and setters...
}
