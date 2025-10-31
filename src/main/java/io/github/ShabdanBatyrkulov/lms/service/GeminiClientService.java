package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.config.ApiKeyProvider;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class GeminiClientService {
    private final String apiKey;
    private final Client.Builder builder;

    @Autowired
    public GeminiClientService(ApiKeyProvider apiKeyProvider) {
        this.apiKey = apiKeyProvider.getGeminiApiKey();
        this.builder = Client.builder();
    }

    public GeminiClientService(ApiKeyProvider apiKeyProvider, Client.Builder builder) {
        this.apiKey = apiKeyProvider.getGeminiApiKey();
        this.builder = builder;
    }

    public String getResponse(String message) {
        Client client = builder.apiKey(this.apiKey).build();
        GenerateContentResponse response = client.models
                .generateContent("gemini-2.5-flash", message, null);
        return response.text();
    }
}
