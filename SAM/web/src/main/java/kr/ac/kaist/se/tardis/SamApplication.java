package kr.ac.kaist.se.tardis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.devtools.autoconfigure.DevToolsDataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={
		DataSourceAutoConfiguration.class, 
		DevToolsDataSourceAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class})
public class SamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamApplication.class, args);
	}
}
