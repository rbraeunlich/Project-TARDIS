package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.Pattern;

public class SetProjectForm {
	
	

	public static final String SHORT_PROJECT_NAME_ERROR = "Project name must contain at least three characters";
	public static final String NO_EXISITING_USER = "No Existing User";
	public static final String WRONG_DUE_DATE_ERROR = "yyyy-MM-dd";
	
	private String projectName;
	private String description;
	private String newMember;
	@Pattern(regexp = "^(\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01]))?$", message = WRONG_DUE_DATE_ERROR)
	private String projectDueDate;
	private boolean oneDayNotification;
	private boolean threeDayNotification;
	private boolean sevenDayNotificaion;



	public String getNewMember() {
		return newMember;
	}

	public void setNewMember(String newMember) {
		this.newMember = newMember;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectDueDate() {
		return projectDueDate;
	}

	public void setProjectDueDate(String projectDueDate) {
		this.projectDueDate = projectDueDate;
	}

	public boolean isOneDayNotification() {
		return oneDayNotification;
	}

	public void setOneDayNotification(boolean oneDayNotification) {
		this.oneDayNotification = oneDayNotification;
	}

	public boolean isThreeDayNotification() {
		return threeDayNotification;
	}

	public void setThreeDayNotification(boolean threeDayNotification) {
		this.threeDayNotification = threeDayNotification;
	}

	public boolean isSevenDayNotificaion() {
		return sevenDayNotificaion;
	}

	public void setSevenDayNotificaion(boolean sevenDayNotificaion) {
		this.sevenDayNotificaion = sevenDayNotificaion;
	}

}
