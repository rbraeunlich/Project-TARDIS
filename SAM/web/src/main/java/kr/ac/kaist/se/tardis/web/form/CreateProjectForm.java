package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateProjectForm {

	public static final String SHORT_PROJECT_NAME_ERROR = "Project name must contain at least three characters";
	public static final String NO_PROJECT_NAME_ERROR = "Project must have a name";

	@NotNull(message= NO_PROJECT_NAME_ERROR)
	@Size(min = 3, message = SHORT_PROJECT_NAME_ERROR)
	private String projectName;

	private String description;

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
