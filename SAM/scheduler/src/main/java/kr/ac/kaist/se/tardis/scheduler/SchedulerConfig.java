package kr.ac.kaist.se.tardis.scheduler;

import java.util.Properties;

import kr.ac.kaist.se.tardis.scheduler.impl.ApplicationContextProvider;
import kr.ac.kaist.se.tardis.scheduler.impl.SchedulerWrapper;
import kr.ac.kaist.se.tardis.scheduler.impl.SpringConnectionProvider;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

@Configuration
public class SchedulerConfig {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Bean
	public ApplicationContextProvider createAppContextProvider(){
		ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
		applicationContextProvider.setApplicationContext(appContext);
		return applicationContextProvider;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@DependsOn("createAppContextProvider")
	public Scheduler createScheduler() throws SchedulerException {
		Properties props = new Properties();
		props.put(StdSchedulerFactory.PROP_DATASOURCE_PREFIX + ".default."
				+ StdSchedulerFactory.PROP_CONNECTION_PROVIDER_CLASS,
				SpringConnectionProvider.class.getName());
		props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS,
				SimpleThreadPool.class.getName());
		//Warning: if we use more threads, we have to activate MVCC for the H2 database
		//see comment in h2.quartz.sql
		props.put(StdSchedulerFactory.PROP_THREAD_POOL_PREFIX + ".threadCount",
				"1");
		props.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS,
				JobStoreTX.class.getName());
		props.put(StdSchedulerFactory.PROP_JOB_STORE_PREFIX
				+ ".driverDelegateClass", StdJDBCDelegate.class.getName());
		props.put(StdSchedulerFactory.PROP_JOB_STORE_PREFIX + ".dataSource",
				"default");
		return new StdSchedulerFactory(props).getScheduler();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public SchedulerWrapper createSchedulerWrapper() {
		try {
			return new SchedulerWrapper(createScheduler());
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

}
