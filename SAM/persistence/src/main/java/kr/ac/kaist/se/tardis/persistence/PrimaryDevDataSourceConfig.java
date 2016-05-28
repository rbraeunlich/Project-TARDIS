package kr.ac.kaist.se.tardis.persistence;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("dev")
public class PrimaryDevDataSourceConfig {

	@Primary
	@Bean
	public DataSource createPrimaryDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.quartz.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.notification.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.project.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.task.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.jobinfo.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.tasknote.sql")
				 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.addmockuser.sql")
				.build();
		return embeddedDatabase;
	}

}
