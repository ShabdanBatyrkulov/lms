import io.github.ShabdanBatyrkulov.lms.config.ApiKeyProvider;
import io.github.ShabdanBatyrkulov.lms.dto.ChatRequestDto;
import io.github.ShabdanBatyrkulov.lms.dto.ChatResponseDto;
import io.github.ShabdanBatyrkulov.lms.service.ChatServiceImpl;
import io.github.ShabdanBatyrkulov.lms.service.GeminiClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class ChatServiceImplTest {

    @Test
    void processMessage_ShouldReturnResponse() {
        ApiKeyProvider apiKeyProvider = Mockito.mock(ApiKeyProvider.class);
        GeminiClientService geminiClientService = Mockito.mock(GeminiClientService.class);

        Mockito.when(apiKeyProvider.getGeminiApiKey()).thenReturn("dummy-key");
        Mockito.when(geminiClientService.getResponse("Hello"))
               .thenReturn("Gemini reply");

        ChatServiceImpl service = new ChatServiceImpl(geminiClientService);

        ChatRequestDto req = new ChatRequestDto();
        req.setMessage("Hello");

        ChatResponseDto res = service.processMessage(req);

        assertThat(res.getResponse()).isEqualTo("Gemini reply");
    }
}
