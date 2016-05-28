package kr.ac.kaist.se.tardis.scheduler.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.scheduler.api.GitHubJobBuilder;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.ProjectJobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.scheduler.api.StandardNotificationBuilder;
import kr.ac.kaist.se.tardis.scheduler.api.TaskJobInfo;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private Scheduler scheduler;

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
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteJob(JobInfo jobInfo) {
		String[] split = jobInfo.getTriggerId().split(".");
		String group = split[0];
		String name = split[1];
		try {
			scheduler.unscheduleJob(TriggerKey.triggerKey(name, group));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private class StandardNotificationBuilderImpl implements StandardNotificationBuilder {

		private static final String EXCEPTION_TEXT_IDS = "Either taskId or projectId can be set, not both!";
		private static final String EXCEPTION_TEXT_DAYS = "Only one date can be set at once!";
		private JobInfo jobInfo;
		private String taskId;
		private String projectId;
		private Date oneDay;
		private Date threeDays;
		private Date sevenDays;

		public StandardNotificationBuilderImpl() {
		}

		@Override
		public JobInfo submit() {
			if (projectId != null) {
				jobInfo = new ProjectJobInfo();
				jobInfo.setId(projectId);
			} else {
				jobInfo = new TaskJobInfo();
				jobInfo.setId(taskId);
			}

			Date dateForJob = threeDays;
			if (threeDays == null && sevenDays == null) {
				dateForJob = oneDay;
			} else if (sevenDays != null) {
				dateForJob = sevenDays;
			}
			JobDetail jobDetail = JobBuilder.newJob(NotificationJob.class)
					.usingJobData(NotificationJob.KEY_ID, taskId == null ? projectId : taskId)
					.usingJobData(NotificationJob.KEY_DUE_DATE, dateForJob.getTime())
					.usingJobData(NotificationJob.KEY_TYPE,
							taskId == null ? NotificationJob.VALUE_PROJECT : NotificationJob.VALUE_TASK)
					.build();
			if (oneDay != null) {
				jobInfo.setJobType(JobType.ONE_DAY);
				Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
						.startAt(new Date(oneDay.getTime() - TimeUnit.DAYS.toMillis(1L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			if (threeDays != null) {
				jobInfo.setJobType(JobType.THREE_DAYS);
				Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
						.startAt(new Date(threeDays.getTime() - TimeUnit.DAYS.toMillis(3L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			if (sevenDays != null) {
				jobInfo.setJobType(JobType.SEVEN_DAYS);
				Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
						.startAt(new Date(sevenDays.getTime() - TimeUnit.DAYS.toMillis(7L))).build();
				SchedulerServiceImpl.this.submitJob(jobDetail, trigger);
			}
			return jobInfo;
		}

		@Override
		public StandardNotificationBuilder forTask(String id) {
			if (projectId != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_IDS);
			}
			this.taskId = id;
			return this;
		}

		@Override
		public StandardNotificationBuilder forProject(String id) {
			if (taskId != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_IDS);
			}
			this.projectId = id;
			return this;
		}

		@Override
		public StandardNotificationBuilder threeDays(Date endDate) {
			if (sevenDays != null || oneDay != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			threeDays = endDate;
			return this;
		}

		@Override
		public StandardNotificationBuilder sevenDays(Date endDate) {
			if (threeDays != null || oneDay != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			sevenDays = endDate;
			return this;
		}

		@Override
		public StandardNotificationBuilder oneDay(Date endDate) {
			if (sevenDays != null || threeDays != null) {
				throw new IllegalArgumentException(EXCEPTION_TEXT_DAYS);
			}
			oneDay = endDate;
			return this;
		}
	}

	private class GitHubJobBuilderImpl implements GitHubJobBuilder {

		private JobInfo jobInfo;
		private String projectId;
		private String githubUrl;

		public GitHubJobBuilderImpl() {
			jobInfo = new ProjectJobInfo();
		}

		@Override
		public GitHubJobBuilder forRepository(String url) {
			jobInfo.setJobType(JobType.GITHUB);
			this.githubUrl = url;
			return this;
		}

		@Override
		public GitHubJobBuilder forProject(String id) {
			jobInfo.setJobType(JobType.GITHUB);
			this.projectId = id;
			return this;
		}

		@Override
		public JobInfo submit() {
			JobDetail jobDetail = JobBuilder.newJob(GitHubJob.class)
					.usingJobData(GitHubJob.KEY_PROJECT_ID, this.projectId)
					.usingJobData(GitHubJob.KEY_GITHUB_URL, this.githubUrl).build();

			Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(2).repeatForever())
					.build();
			SchedulerServiceImpl.this.submitJob(jobDetail, trigger);

			return jobInfo;
		}

	}

}
