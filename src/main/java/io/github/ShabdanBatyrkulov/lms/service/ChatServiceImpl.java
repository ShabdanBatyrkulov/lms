package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public ChatResponseDto processMessage(ChatRequestDto request) {
        // TODO: Implement actual AI service integration here
        // This is a placeholder implementation
        String response = "Echo: " + request.getMessage();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        return new ChatResponseDto(response, timestamp);
    }
}