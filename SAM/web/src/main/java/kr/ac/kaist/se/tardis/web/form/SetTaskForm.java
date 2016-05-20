package kr.ac.kaist.se.tardis.web.form;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class SetTaskForm {
	public static final String SHORT_TASK_NAME_ERROR = "Task name must contain at least three characters";
	public static final String NO_TASK_NAME_ERROR = "Task must have a name";
	public static final String DUP_TASK_NAME_ERROR = "Task must have a unique name";
	public static final String NO_DUE_DATE_ERROR = "Task must set due date";
	public static final String NO_OWNER_ERROR = "Task must assigned to owner";
	public static final String OWNER_NOT_EXIST_ERROR = "Owner is not registered user";
	public static final String NO_EXISITING_MEMBER = "No Existing Member";
	
	@NotNull
	private String projectId;
	
	private String taskName;

	private String taskDescription;

	// NotNull(message= NO_DUE_DATE_ERROR)
	@DateTimeFormat (pattern="yyyy-MM-dd") 
	private Date dueDate;
	
	private String owner;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
