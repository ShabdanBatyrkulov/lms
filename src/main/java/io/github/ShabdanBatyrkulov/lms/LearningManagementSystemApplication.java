package io.github.ShabdanBatyrkulov.lms;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningManagementSystemApplication {

	public static void main(String[] args) {
		// Load .env file into environment
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );

		SpringApplication.run(LearningManagementSystemApplication.class, args);
	}

}
