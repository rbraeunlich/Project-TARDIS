package kr.ac.kaist.se.tardis.web.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.web.form.FormWithNotification;

/**
 * Helper class to handle the complex logic of creating and deleting jobs
 */
public class JobHelper {

	public static Project createAndDeleteJobsForProject(SchedulerService schedulerService, Project project,
			FormWithNotification form, Date dueDate) {
		Set<JobInfo> newJobs = createAndDeleteJobs(schedulerService, project.getId().getId(), form, dueDate,
				project.getAllJobInfos());
		for (JobInfo jobInfo : newJobs) {
			project.addJobInfo(jobInfo);
		}
		return project;
	}

	public static Task createAndDeleteJobsForTask(SchedulerService schedulerService, Task task,
			FormWithNotification form, Date dueDate) {
		Set<JobInfo> newJobs = createAndDeleteJobs(schedulerService, task.getId().getId(), form, dueDate,
				task.getAllJobInfos());
		for (JobInfo jobInfo : newJobs) {
			task.addJobInfo(jobInfo);
		}
		return task;

	}

	private static Set<JobInfo> createAndDeleteJobs(SchedulerService schedulerService, String id,
			FormWithNotification form, Date dueDate, Set<JobInfo> existingJobs) {
		// first see if a new job has to be added
		Set<JobInfo> newJobInfos = new HashSet<>();
		Set<JobType> existingJobTypes = existingJobs.stream().map(j -> j.getJobType()).collect(Collectors.toSet());
		boolean createOneDayJob = false;
		boolean createThreeDaysJob = false;
		boolean createSevenDaysJob = false;
		if (!existingJobTypes.contains(JobType.ONE_DAY) && form.isOneDayNotification()) {
			createOneDayJob = true;
		}
		if (!existingJobTypes.contains(JobType.THREE_DAYS) && form.isThreeDayNotification()) {
			createThreeDaysJob = true;
		}
		if (!existingJobTypes.contains(JobType.SEVEN_DAYS) && form.isSevenDayNotificaion()) {
			createSevenDaysJob = true;
		}
		// second check if existing job has to be removed
		for (JobInfo jobInfo : existingJobs) {
			if (jobInfo.getJobType().equals(JobType.ONE_DAY) && !form.isOneDayNotification()) {
				schedulerService.deleteJob(jobInfo);
			}
			if (jobInfo.getJobType().equals(JobType.THREE_DAYS) && !form.isThreeDayNotification()) {
				schedulerService.deleteJob(jobInfo);
			}
			if (jobInfo.getJobType().equals(JobType.SEVEN_DAYS) && !form.isSevenDayNotificaion()) {
				schedulerService.deleteJob(jobInfo);
			}
		}
		// lastly, create the new jobs
		if (createOneDayJob) {
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(id).oneDay(dueDate).submit();
			newJobInfos.add(jobInfo);
		}
		if (createThreeDaysJob) {
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(id).threeDays(dueDate).submit();
			newJobInfos.add(jobInfo);
		}
		if (createSevenDaysJob) {
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(id).sevenDays(dueDate).submit();
			newJobInfos.add(jobInfo);
		}
		return newJobInfos;
	}

}
