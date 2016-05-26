package kr.ac.kaist.se.tardis.scheduler;

import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class SchedulerConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public SchedulerFactoryBean createScheduler() throws SchedulerException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJobFactory(new SpringBeanJobFactory());
		return factory;
	}

}
