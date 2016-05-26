package kr.ac.kaist.se.tardis.web.controller;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.web.form.FormWithNotification;

/**
 * Helper class to handle the complex logic of creating and deleting jobs
 */
public class JobHelper {

	public static Project createAndDeleteJobsForProject(SchedulerService schedulerService, Project project,
			FormWithNotification form, Date dueDate) {
		Set<JobInfo> existingJobs = project.getAllJobInfos();
		// first see if a new job has to be added
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
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(project.getId().getId())
					.oneDay(dueDate).submit();
			project.addJobInfo(jobInfo);
		}
		if (createThreeDaysJob) {
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(project.getId().getId())
					.threeDays(dueDate).submit();
			project.addJobInfo(jobInfo);
		}
		if (createSevenDaysJob) {
			JobInfo jobInfo = schedulerService.createNotificationBuilder().forProject(project.getId().getId())
					.sevenDays(dueDate).submit();
			project.addJobInfo(jobInfo);
		}
		return project;
	}

}
