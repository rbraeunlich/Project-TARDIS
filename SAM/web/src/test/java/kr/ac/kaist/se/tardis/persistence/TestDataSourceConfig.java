package kr.ac.kaist.se.tardis.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class TestDataSourceConfig {

	@Bean(name = "userDS")
	public DataSource createUsersDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.addScript("kr/ac/kaist/se/tardis/users/sql/h2.user.sql")
				.build();
		return embeddedDatabase;
	}
	
	@Bean
	@Primary
	public DataSource createPrimaryDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
				.build();
		return embeddedDatabase;
	}

}
