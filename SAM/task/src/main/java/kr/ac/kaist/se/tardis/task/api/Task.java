package kr.ac.kaist.se.tardis.task.api;

import java.util.Date;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.scheduler.api.JobOwner;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

public interface Task extends JobOwner {

	String getName();

	void setName(String name);

	TaskId getId();

	Project getProject();

	String getDescription();

	void setDescription(String des);

	Date getDueDate();

	void setDueDate(Date d);

	void setOwner(String member);

	String getOwner();

	TaskState getTaskState();

	void setTaskState(TaskState taskState);

	public int getTaskProgress();

	public void setTaskProgress(int taskProgress);

	ProjectId getProjectId();

}
