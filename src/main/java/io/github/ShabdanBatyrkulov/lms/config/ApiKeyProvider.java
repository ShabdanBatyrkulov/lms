package io.github.ShabdanBatyrkulov.lms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider {

    @Value("${api.gemini.api-key}")
    private String geminiApiKey;

    public String getGeminiApiKey() {
        return geminiApiKey;
    }
}
