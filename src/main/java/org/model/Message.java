package org.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;

    // Getters and setters...
}
