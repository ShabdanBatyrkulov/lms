package io.github.ShabdanBatyrkulov.lms.controller;

import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import io.github.ShabdanBatyrkulov.lms.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDto> processMessage(@RequestBody ChatRequestDto request) {
        ChatResponseDto response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }
}