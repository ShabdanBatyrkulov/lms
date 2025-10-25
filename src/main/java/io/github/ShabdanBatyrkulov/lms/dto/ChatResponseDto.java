package io.github.ShabdanBatyrkulov.lms.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatResponseDto {
    @NotBlank(message = "Respomse is required")
    private String response;

    @NotBlank(message = "Timestamp is required")
    private String timestamp;

    public ChatResponseDto() {
    }

    public ChatResponseDto(String response, String timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}