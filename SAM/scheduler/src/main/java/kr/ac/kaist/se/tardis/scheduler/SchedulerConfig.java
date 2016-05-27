package kr.ac.kaist.se.tardis.scheduler;

import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import kr.ac.kaist.se.tardis.scheduler.impl.AutowiringSpringBeanJobFactory;

@Configuration
public class SchedulerConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext ctx;
	
	@Bean
	public SchedulerFactoryBean createScheduler() throws SchedulerException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(ctx);
		factory.setJobFactory(jobFactory);
		return factory;
	}

}
