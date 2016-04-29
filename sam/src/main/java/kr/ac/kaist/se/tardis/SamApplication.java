package kr.ac.kaist.se.tardis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamApplication.class, args);
	}
}
