package io.github.ShabdanBatyrkulov.lms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import io.github.ShabdanBatyrkulov.lms.config.JwtAuthenticationFilter;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LearningManagementSystemApplicationTests {
    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }
}
