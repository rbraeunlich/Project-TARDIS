package kr.ac.kaist.se.tardis.scheduler.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StandardNotificationBuilderImplTest.TestConfig.class)
public class StandardNotificationBuilderImplTest {

	@Autowired
	private SchedulerService service;

	@Test
	public void createThreeDaysJob() {
		Date now = new Date();
		Date threeDaysAgo = new Date(now.getTime() - TimeUnit.DAYS.toMillis(3L));
		String id = "123";
		JobInfo jobInfo = service.createNotificationBuilder().forProject(id)
				.threeDays(now).submit();

		JobDetail job = TestConfig.jobDetailCaptor.getValue();
		assertThat(job, is(notNullValue()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_DUE_DATE), is(now.getTime()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_ID), is(id));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_TYPE), is(NotificationJob.VALUE_PROJECT));
		Trigger trigger = TestConfig.triggerCaptor.getValue();
		assertThat(trigger, is(notNullValue()));
		assertThat(trigger.getFinalFireTime(), is(equalTo(threeDaysAgo)));

		assertThat(jobInfo.getId(), is(notNullValue()));
		assertThat(jobInfo.getJobType(), is(JobType.THREE_DAYS));
	}
	

	@Test
	public void createSevenDaysJob() {
		Date now = new Date();
		Date sevenDaysAgo = new Date(now.getTime() - TimeUnit.DAYS.toMillis(7L));
		String id = "123";
		JobInfo jobInfo = service.createNotificationBuilder().forTask(id)
				.sevenDays(now).submit();

		JobDetail job = TestConfig.jobDetailCaptor.getValue();
		assertThat(job, is(notNullValue()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_DUE_DATE), is(now.getTime()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_ID), is(id));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_TYPE), is(NotificationJob.VALUE_TASK));
		Trigger trigger = TestConfig.triggerCaptor.getValue();
		assertThat(trigger, is(notNullValue()));
		assertThat(trigger.getFinalFireTime(), is(equalTo(sevenDaysAgo)));

		assertThat(jobInfo.getId(), is(notNullValue()));
		assertThat(jobInfo.getJobType(), is(JobType.SEVEN_DAYS));
	}
	
	@Test
	public void createFourteenDaysJob() {
		Date now = new Date();
		Date fourteenDaysAgo = new Date(now.getTime() - TimeUnit.DAYS.toMillis(14L));
		String id = "123";
		JobInfo jobInfo = service.createNotificationBuilder().forProject(id)
				.fourteenDays(now).submit();

		JobDetail job = TestConfig.jobDetailCaptor.getValue();
		assertThat(job, is(notNullValue()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_DUE_DATE), is(now.getTime()));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_ID), is(id));
		assertThat(job.getJobDataMap().get(NotificationJob.KEY_TYPE), is(NotificationJob.VALUE_PROJECT));
		Trigger trigger = TestConfig.triggerCaptor.getValue();
		assertThat(trigger, is(notNullValue()));
		assertThat(trigger.getFinalFireTime(), is(equalTo(fourteenDaysAgo)));

		assertThat(jobInfo.getId(), is(notNullValue()));
		assertThat(jobInfo.getJobType(), is(JobType.FOURTEEN_DAYS));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void onlyOneIdAtOnce(){
		service.createNotificationBuilder().forProject("123").forTask("456");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void onlyOneDateAtOnce(){
		service.createNotificationBuilder().sevenDays(new Date()).fourteenDays(new Date());
	}

	@Configuration
	@ComponentScan
	public static class TestConfig {

		private Scheduler mockScheduler;

		private static ArgumentCaptor<JobDetail> jobDetailCaptor;

		private static ArgumentCaptor<Trigger> triggerCaptor;

		@Bean
		public SchedulerWrapper createMockSchedulerWrapper()
				throws SchedulerException {
			mockScheduler = mock(Scheduler.class);
			jobDetailCaptor = ArgumentCaptor.forClass(JobDetail.class);
			triggerCaptor = ArgumentCaptor.forClass(Trigger.class);
			when(
					mockScheduler.scheduleJob(jobDetailCaptor.capture(),
							triggerCaptor.capture())).thenReturn(new Date());
			return new SchedulerWrapper(mockScheduler);
		}

	}
}
