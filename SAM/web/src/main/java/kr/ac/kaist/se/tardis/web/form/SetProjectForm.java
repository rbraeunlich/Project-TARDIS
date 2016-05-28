package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.Pattern;

public class SetProjectForm extends FormWithNotification {

	public static final String SHORT_PROJECT_NAME_ERROR = "Project name must contain at least three characters";
	public static final String NO_EXISITING_USER = "No Existing User";
	public static final String NOTIFICATION_WITHOUT_DUE_DATE = "To set notifications, a due date must be picked";
	public static final String WRONG_URL = "URL must be correct https address";

	private String projectName;
	private String description;
	private String newMember;

	@Pattern(regexp = "^((https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)?$", message = WRONG_URL)
	private String gitHubUrl;

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

	public String getGitHubUrl() {
		return gitHubUrl;
	}

	public void setGitHubUrl(String githubUrl) {
		this.gitHubUrl = githubUrl;
	}

}
