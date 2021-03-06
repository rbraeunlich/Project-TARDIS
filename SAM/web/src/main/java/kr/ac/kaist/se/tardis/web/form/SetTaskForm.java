package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SetTaskForm extends FormWithNotification {
	public static final String SHORT_TASK_NAME_ERROR = "Task name must contain at least three characters";
	public static final String NO_TASK_NAME_ERROR = "Task must have a name";
	public static final String DUP_TASK_NAME_ERROR = "Task must have a unique name";
	public static final String NO_DUE_DATE_ERROR = "Task must set due date";
	public static final String OWNER_NOT_EXIST_ERROR = "Owner is not registered user";
	public static final String NO_EXISITING_MEMBER = "No Existing Member";

	@NotNull
	private String projectId;

	@NotNull
	@Size(min = 3, message = SHORT_TASK_NAME_ERROR)
	private String taskName;

	private String taskDescription;

	@Min(0)
	@Digits(integer = 10, fraction = 0)
	private Integer key;

	@NotNull
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

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

}
