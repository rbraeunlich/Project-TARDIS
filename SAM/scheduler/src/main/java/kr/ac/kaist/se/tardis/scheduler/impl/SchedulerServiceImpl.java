package kr.ac.kaist.se.tardis.scheduler.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import kr.ac.kaist.se.tardis.scheduler.api.GitHubJobBuilder;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.scheduler.api.StandardNotificationBuilder;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private SchedulerWrapper schedulerWrapper;

	@Override
	public GitHubJobBuilder createGitHubJobBuilder() {
		return new GitHubJobBuilderImpl();
	}

	@Override
	public StandardNotificationBuilder createNotificationBuilder() {
		return new StandardNotificationBuilderImpl();
	}

	private void submitJob(JobDetail jobDetail, Trigger trigger) {
		try {
			schedulerWrapper.getScheduler().scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private class StandardNotificationBuilderImpl implements
			StandardNotificationBuilder {

		private static final String EXCEPTION_TEXT_IDS = "Either taskId or projectId can be set, not both!";
		private static final String EXCEPTION_TEXT_DAYS = "Only one date can be set at once!";
		private JobInfo jobInfo;
		private String taskId;
		private String projectId;
		private Date threeDays;
		private Date sevenDays;
		private Date fourteenDays;

		public StandardNotificationBuilderImpl() {
			jobInfo = new JobInfo();
		}

		@Override
		public JobInfo submit() {
			Date dateForJob = threeDays;
			if (threeDays == null && sevenDays == null) {
				dateForJob = fourteenDays;
			} else if (sevenDays != null) {
				dateForJob = sevenDays;
			}
			JobDetail jobDetail = JobBuilder
					.newJob(NotificationJob.class)
					.usingJobData(NotificationJob.KEY_ID,
							taskId == null ? projectId : taskId)
					.usingJobData(NotificationJob.KEY_DUE_DATE,
							dateForJob.getTime())
					.usingJobData(
							NotificationJob.KEY_TYPE,
							taskId == null ? NotificationJob.VALUE_PROJECT
									: NotificationJob.VALUE_TASK).build();
			if (threeDays != null) {
				Trigger trigger = TriggerBuilder
						.newTrigger()
						.forJob(jobDetail)
						.startAt(
								new Date(threeDays.getTime()
										- TimeUnit.DAYS.toMillis(3L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			if (sevenDays != null) {
				Trigger trigger = TriggerBuilder
						.newTrigger()
						.forJob(jobDetail)
						.startAt(
								new Date(sevenDays.getTime()
										- TimeUnit.DAYS.toMillis(7L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			if (fourteenDays != null) {
				Trigger trigger = TriggerBuilder
						.newTrigger()
						.forJob(jobDetail)
						.startAt(
								new Date(fourteenDays.getTime()
										- TimeUnit.DAYS.toMillis(14L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			return jobInfo;
		}

		@Override
		public StandardNotificationBuilder forTask(String id) {
			if (projectId != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_IDS);
			}
			jobInfo.setTaskId(id);
			this.taskId = id;
			return this;
		}

		@Override
		public StandardNotificationBuilder forProject(String id) {
			if (taskId != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_IDS);
			}
			jobInfo.setProjectId(id);
			this.projectId = id;
			return this;
		}

		@Override
		public StandardNotificationBuilder threeDays(Date endDate) {
			if (sevenDays != null || fourteenDays != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			jobInfo.setJobType(JobType.THREE_DAYS);
			threeDays = endDate;
			return this;
		}

		@Override
		public StandardNotificationBuilder sevenDays(Date endDate) {
			if (threeDays != null || fourteenDays != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			jobInfo.setJobType(JobType.SEVEN_DAYS);
			sevenDays = endDate;
			return this;
		}

		@Override
		public StandardNotificationBuilder fourteenDays(Date endDate) {
			if (sevenDays != null || threeDays != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			jobInfo.setJobType(JobType.FOURTEEN_DAYS);
			fourteenDays = endDate;
			return this;
		}
	}

	private class GitHubJobBuilderImpl implements GitHubJobBuilder {

	}
}
