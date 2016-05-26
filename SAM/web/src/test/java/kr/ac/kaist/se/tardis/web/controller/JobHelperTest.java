package kr.ac.kaist.se.tardis.web.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.scheduler.api.StandardNotificationBuilder;
import kr.ac.kaist.se.tardis.web.form.FormWithNotification;

public class JobHelperTest {

	@Test
	public void createJobsWhenNoneExists() throws ParseException{
		SchedulerService schedulerService = mock(SchedulerService.class);
		StandardNotificationBuilder notificationBuilderMock = mock(StandardNotificationBuilder.class);
		when(schedulerService.createNotificationBuilder()).thenReturn(notificationBuilderMock);
		when(notificationBuilderMock.forProject(anyString())).thenReturn(notificationBuilderMock);
		when(notificationBuilderMock.oneDay(any(Date.class))).thenReturn(notificationBuilderMock);
		when(notificationBuilderMock.threeDays(any(Date.class))).thenReturn(notificationBuilderMock);
		when(notificationBuilderMock.sevenDays(any(Date.class))).thenReturn(notificationBuilderMock);
		when(notificationBuilderMock.submit()).thenReturn(new JobInfo());
		FormWithNotification formWithNotification = new FormWithNotification();
		String dueDate = "2016-01-01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		formWithNotification.setDueDate(dueDate);
		formWithNotification.setOneDayNotification(true);
		formWithNotification.setThreeDayNotification(true);
		formWithNotification.setSevenDayNotificaion(true);
		Project project = mock(Project.class);
		when(project.getId()).thenReturn(ProjectIdFactory.generateProjectId());
		when(project.getAllJobInfos()).thenReturn(Collections.emptySet());
		
		JobHelper.createAndDeleteJobsForProject(schedulerService, project, formWithNotification, dateFormat.parse(dueDate));
		
		verify(project, times(3)).addJobInfo(any(JobInfo.class));
		verify(notificationBuilderMock, times(1)).oneDay(any(Date.class));
		verify(notificationBuilderMock, times(1)).threeDays(any(Date.class));
		verify(notificationBuilderMock, times(1)).sevenDays(any(Date.class));
	}
	
	@Test
	public void deleteJobs(){
		SchedulerService schedulerService = mock(SchedulerService.class);
		FormWithNotification form = new FormWithNotification();
		Project projectMock = mock(Project.class);
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobType(JobType.ONE_DAY);
		JobInfo jobInfo2 = new JobInfo();
		jobInfo2.setJobType(JobType.THREE_DAYS);
		JobInfo jobInfo3 = new JobInfo();
		jobInfo3.setJobType(JobType.SEVEN_DAYS);
		when(projectMock.getAllJobInfos()).thenReturn(new HashSet<>(Arrays.asList(jobInfo, jobInfo2, jobInfo3)));
		
		JobHelper.createAndDeleteJobsForProject(schedulerService, projectMock, form, new Date());
		
		verify(schedulerService, times(1)).deleteJob(eq(jobInfo));
		verify(schedulerService, times(1)).deleteJob(eq(jobInfo2));
		verify(schedulerService, times(1)).deleteJob(eq(jobInfo3));
	}
	
	@Test
	public void doNotDeleteExistingJob(){
		SchedulerService schedulerService = mock(SchedulerService.class);
		FormWithNotification form = new FormWithNotification();
		Project projectMock = mock(Project.class);
		JobInfo jobInfo = new JobInfo();
		jobInfo.setJobType(JobType.ONE_DAY);
		when(projectMock.getAllJobInfos()).thenReturn(Collections.singleton(jobInfo));
		
		JobHelper.createAndDeleteJobsForProject(schedulerService, projectMock, form, new Date());
		
		verify(schedulerService, never()).deleteJob(any(JobInfo.class));
	}
	
}
