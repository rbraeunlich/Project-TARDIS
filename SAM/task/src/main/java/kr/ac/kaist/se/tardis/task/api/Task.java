package kr.ac.kaist.se.tardis.task.api;

import java.util.Date;
import java.util.Optional;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

public interface Task {

	void setName(String name);

	TaskId getId();
	
	ProjectId getProjectId();

	String getName();
	
	void setDescription(String des);
	
	String getDescription();
	
	Date getDueDate();
	
	void setDueDate(Date d);
	
	void setOwner(String member);
	
	String getTaskOwner();
	
	String getProjectOwner();
	
	TaskState getTaskState();
	void setTaskState(TaskState taskState);

}
