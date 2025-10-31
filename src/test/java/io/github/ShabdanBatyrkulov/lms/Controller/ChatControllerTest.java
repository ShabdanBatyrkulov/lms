package io.github.ShabdanBatyrkulov.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import io.github.ShabdanBatyrkulov.lms.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void processMessage_Successful() throws Exception {
        // Arrange
        ChatRequestDto request = new ChatRequestDto("Hello, Gemini!");
        ChatResponseDto response = new ChatResponseDto("Hi there!", 
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        when(chatService.processMessage(any(ChatRequestDto.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hi there!"))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void processMessage_EmptyMessage_ShouldReturnBadRequest() throws Exception {
        ChatRequestDto request = new ChatRequestDto("");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("message: Message is required")));
    }

    @Test
    void processMessage_ServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
        ChatRequestDto request = new ChatRequestDto("Trigger error");

        doThrow(new RuntimeException("Something went wrong"))
                .when(chatService).processMessage(any(ChatRequestDto.class));

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Something went wrong"));
    }
}
