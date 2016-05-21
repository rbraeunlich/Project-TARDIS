package kr.ac.kaist.se.tardis.scheduler.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Wraps a {@link Scheduler} so we can combine the lifecycle callbacks with its
 * methods.
 */
public class SchedulerWrapper {

	private final Scheduler scheduler;

	public SchedulerWrapper(Scheduler s) {
		this.scheduler = s;
	}

	@PostConstruct
	public void init() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@PreDestroy
	public void destroy() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public Scheduler getScheduler(){
		return scheduler;
	}
}
