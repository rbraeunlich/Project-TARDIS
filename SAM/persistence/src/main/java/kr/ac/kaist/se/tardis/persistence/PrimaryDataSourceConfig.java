package kr.ac.kaist.se.tardis.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class PrimaryDataSourceConfig {

	@Primary
	@Bean
	public DataSource createPrimaryDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.quartz.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.notification.sql")
				.build();
		return embeddedDatabase;
	}

}
