package kr.ac.kaist.se.tardis.scheduler.impl;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StopWatch.TaskInfo;

public class NotificationJob implements Job {

	public static final String KEY_ID = "ID";
	public static final String VALUE_TASK = "task";
	public static final String VALUE_PROJECT = "project";
	public static final String KEY_TYPE = "TYPE";

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String id = (String) context.get(KEY_ID);
		String type = (String) context.get(KEY_TYPE);
		if(type.equals(VALUE_TASK)){
			addNotificationToTask(TaskIdFactory.valueOf(id));
		} else {
			addNotificationToProject(ProjectIdFactory.valueOf(id));
		}
	}

	private void addNotificationToProject(ProjectId valueOf) {
		// TODO Auto-generated method stub
		
	}

	private void addNotificationToTask(TaskId valueOf) {
		// TODO Auto-generated method stub
		
	}

}
