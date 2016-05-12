package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateProjectForm {
	
	@NotNull
	@Size(min=3, message="Project name must contain at least three characters")
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
