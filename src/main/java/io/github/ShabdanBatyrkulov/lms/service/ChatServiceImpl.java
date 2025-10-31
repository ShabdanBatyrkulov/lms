package io.github.ShabdanBatyrkulov.lms.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.ShabdanBatyrkulov.lms.config.ApiKeyProvider;
import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    private final ApiKeyProvider apiKeyProvider;

    public ChatServiceImpl(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    @Override
    public ChatResponseDto processMessage(ChatRequestDto request) {
        Client.Builder builder = Client.builder();
        Client client = builder.apiKey(apiKeyProvider.getGeminiApiKey()).build();
        
        // TODO: Implement actual AI service integration here
        // This is a placeholder implementation

        GenerateContentResponse response =
            client.models.generateContent(
                "gemini-2.5-flash",
                request.getMessage(),
                null);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        return new ChatResponseDto(response.text(), timestamp);
    }
}