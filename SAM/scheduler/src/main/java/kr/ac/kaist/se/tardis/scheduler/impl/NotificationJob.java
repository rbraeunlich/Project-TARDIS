package kr.ac.kaist.se.tardis.scheduler.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

@Component
public class NotificationJob extends QuartzJobBean {

	public static final String KEY_ID = "ID";
	public static final String VALUE_TASK = "task";
	public static final String VALUE_PROJECT = "project";
	public static final String KEY_TYPE = "TYPE";
	public static final String KEY_DUE_DATE = "DUE_DATE";

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private TaskService taskService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String id = (String) context.get(KEY_ID);
		String type = (String) context.get(KEY_TYPE);
		Date dueDate = new Date((Long) context.get(KEY_DUE_DATE));
		if (type.equals(VALUE_TASK)) {
			addNotificationToTask(TaskIdFactory.valueOf(id), dueDate);
		} else {
			addNotificationToProject(ProjectIdFactory.valueOf(id), dueDate);
		}
	}

	private void addNotificationToProject(ProjectId projectId, Date dueDate) {
		Optional<Project> optional = projectService.findProjectById(projectId);
		if (!optional.isPresent()) {
			// project got deleted, nothing left to do
			return;
		}
		Project project = optional.get();
		HashSet<String> peopleToNotify = new HashSet<>(project.getProjectMembers());
		peopleToNotify.add(project.getProjectOwner());
		for (String person : peopleToNotify) {
			notificationService.createNotification(person, "Project " + project.getName() + " is due on " + dueDate,
					new Date());
		}
	}

	private void addNotificationToTask(TaskId id, Date dueDate) {
		Optional<Task> optional = taskService.findTaskById(id);
		if (!optional.isPresent()) {
			// task got deleted, nothing left to do
			return;
		}
		Task task = optional.get();
		ProjectId projectId = task.getProjectId();
		Optional<Project> optionalProject = projectService.findProjectById(projectId);
		if (!optionalProject.isPresent()) {
			LoggerFactory.getLogger(getClass()).warn("Task " + task.getId().getId() + " has no project anymore.");
		}
		Project project = optionalProject.get();
		notificationService.createNotification(task.getOwner(),
				"Task " + task.getName() + " from project " + project.getName() + " is due on " + dueDate, new Date());
	}

}
