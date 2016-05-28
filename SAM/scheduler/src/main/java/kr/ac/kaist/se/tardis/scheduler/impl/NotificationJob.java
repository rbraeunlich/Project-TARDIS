package kr.ac.kaist.se.tardis.scheduler.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

	public static final String KEY_ID = "id";
	public static final String VALUE_TASK = "task";
	public static final String VALUE_PROJECT = "project";
	public static final String KEY_TYPE = "type";
	public static final String KEY_DUE_DATE = "dueDate";

	@Autowired
	private ProjectService projectService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private TaskService taskService;

	private Long dueDate;

	private String id;

	private String type;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (getType().equals(VALUE_TASK)) {
			addNotificationToTask(TaskIdFactory.valueOf(getId()), new Date(dueDate));
		} else {
			addNotificationToProject(ProjectIdFactory.valueOf(getId()), new Date(dueDate));
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
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			notificationService.createNotification(person,
					"Project " + project.getName() + " is due on " + format.format(dueDate), new Date());
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
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		notificationService.createNotification(task.getOwner(), "Task " + task.getName() + " from project "
				+ project.getName() + " is due on " + format.format(dueDate), new Date());
	}

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}
