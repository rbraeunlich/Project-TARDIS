package kr.ac.kaist.se.tardis.task.api;

import java.util.Date;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

public interface Task {

	String getName();
	void setName(String name);

	TaskId getId();

	ProjectId getProjectId();


	String getDescription();
	void setDescription(String des);

	Date getDueDate();
	void setDueDate(Date d);

	
	
	String getOwner();
	
	
	TaskState getTaskState();
	void setTaskState(TaskState taskState);
	
	public int getTaskProgress();

	public void setTaskProgress(int taskProgress);

}
