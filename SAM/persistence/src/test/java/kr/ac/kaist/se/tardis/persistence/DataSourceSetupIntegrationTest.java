package kr.ac.kaist.se.tardis.persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes = {PrimaryDataSourceConfig.class})
public class DataSourceSetupIntegrationTest {
	
	@Autowired
	private DataSource primaryDS;
	
	@Test
	public void dataSourcesSetUp() throws SQLException{
		assertThat(primaryDS, is(notNullValue()));
	}
	
}
