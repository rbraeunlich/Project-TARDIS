package kr.ac.kaist.se.tardis.scheduler.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.quartz.utils.ConnectionProvider;

public class SpringConnectionProvider implements ConnectionProvider {

	private DataSource dataSource;

	public SpringConnectionProvider() {
		this.dataSource = ApplicationContextProvider.getApplicationContext()
				.getBean(DataSource.class);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void shutdown() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() throws SQLException {
		// TODO Auto-generated method stub

	}

}
