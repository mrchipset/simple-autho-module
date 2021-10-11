package simple.autho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthoApplication.class, args);
	}

}
