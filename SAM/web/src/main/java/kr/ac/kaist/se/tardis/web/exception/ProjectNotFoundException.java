package kr.ac.kaist.se.tardis.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such project")
public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6903984657901299028L;

	public ProjectNotFoundException(ProjectId id) {
		super("The project with the id " + id.getId() + " could not be found.");
	}

}
