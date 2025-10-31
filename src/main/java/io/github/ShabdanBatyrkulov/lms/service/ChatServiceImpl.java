package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatServiceImpl implements ChatService {
    private final GeminiClientService geminiClientService;

    @Autowired
    public ChatServiceImpl(GeminiClientService geminiClientService) {
        this.geminiClientService = geminiClientService;
    }

    @Override
    public ChatResponseDto processMessage(ChatRequestDto request) {
        String response = geminiClientService.getResponse(request.getMessage());

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        return new ChatResponseDto(response, timestamp);
    }
}
