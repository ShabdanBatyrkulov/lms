package io.github.ShabdanBatyrkulov.lms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import io.github.ShabdanBatyrkulov.lms.config.JwtAuthenticationFilter;

@SpringBootTest
class LearningManagementSystemApplicationTests {

	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Test
	void contextLoads() {
	}

}
