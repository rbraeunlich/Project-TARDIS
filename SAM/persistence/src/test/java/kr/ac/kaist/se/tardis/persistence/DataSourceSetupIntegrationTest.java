package kr.ac.kaist.se.tardis.persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.users.impl.persistence.UserDataSourceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes = {PrimaryDataSourceConfig.class,UserDataSourceConfig.class})
public class DataSourceSetupIntegrationTest {
	
	@Autowired
	@Qualifier("userDS")
	private DataSource userDS;
	
	@Autowired
	private DataSource primaryDS;
	
	@Test
	public void dataSourcesSetUp() throws SQLException{
		//TODO extend this test when the DB configuration progresses
		assertThat(primaryDS, is(notNullValue()));
	}
	
	@Test
	public void bothDataSourcesWorkTogether() throws SQLException{
		//TODO extend this test when the DB configuration progresses
		assertThat(userDS, is(notNullValue()));
		assertThat(primaryDS, is(notNullValue()));
	}
}
