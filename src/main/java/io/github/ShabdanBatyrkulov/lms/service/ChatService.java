package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;

public interface ChatService {
    /**
     * Process a chat message and return a response
     * @param request The chat request containing the user's message
     * @return ChatResponseDto containing the AI's response and timestamp
     */
    ChatResponseDto processMessage(ChatRequestDto request);
}