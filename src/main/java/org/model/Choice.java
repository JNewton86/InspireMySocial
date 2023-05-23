package org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {

    @JsonProperty("index")
    private int index;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("finish_reason")
    private String finishReason;

    // Getters and setters...
}
