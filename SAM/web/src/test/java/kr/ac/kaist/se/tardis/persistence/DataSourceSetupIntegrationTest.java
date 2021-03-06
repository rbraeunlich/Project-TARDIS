package kr.ac.kaist.se.tardis.persistence;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import kr.ac.kaist.se.tardis.SamApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest
@SpringApplicationConfiguration(classes = SamApplication.class)
public class DataSourceSetupIntegrationTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("userDS")
	private DataSource userDS;
	
	@Test
	public void dataSourcesSetUp() throws SQLException{
		assertThat(dataSource, is(notNullValue()));
		assertThat(userDS, is(notNullValue()));
		assertThat(dataSource.getConnection().getMetaData().getURL(), is(not(equalTo(userDS.getConnection().getMetaData().getURL()))));
	}

}
