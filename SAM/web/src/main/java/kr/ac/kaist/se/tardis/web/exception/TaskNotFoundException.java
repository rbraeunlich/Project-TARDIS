package kr.ac.kaist.se.tardis.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.ac.kaist.se.tardis.task.impl.id.TaskId;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such task")
public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4468755170060668112L;

	public TaskNotFoundException(TaskId id) {
		super("The task with the id " + id.getId() + " could not be found.");
	}

}
