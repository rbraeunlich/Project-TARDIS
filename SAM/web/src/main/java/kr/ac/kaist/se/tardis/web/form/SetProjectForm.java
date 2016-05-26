package kr.ac.kaist.se.tardis.web.form;

public class SetProjectForm extends FormWithNotification {
	
	

	public static final String SHORT_PROJECT_NAME_ERROR = "Project name must contain at least three characters";
	public static final String NO_EXISITING_USER = "No Existing User";
	public static final String NOTIFICATION_WITHOUT_DUE_DATE = "To set notifications, a due date must be picked";
	
	private String projectName;
	private String description;
	private String newMember;




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

}
