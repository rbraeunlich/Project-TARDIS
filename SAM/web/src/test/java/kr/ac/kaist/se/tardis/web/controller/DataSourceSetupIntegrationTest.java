package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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

import kr.ac.kaist.se.tardis.SamApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes = SamApplication.class)
public class DataSourceSetupIntegrationTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("userDS")
	private DataSource userDS;
	
	@Test
	public void dataSourcesSetUp() throws SQLException{
		//TODO extend this test when the DB configuration progresses
		assertThat(dataSource, is(notNullValue()));
		assertThat(userDS, is(notNullValue()));
		assertThat(dataSource.getConnection().getMetaData().getURL(), is(not(equalTo(userDS.getConnection().getMetaData().getURL()))));
	}

}
