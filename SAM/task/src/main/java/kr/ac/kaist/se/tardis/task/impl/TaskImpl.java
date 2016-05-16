package kr.ac.kaist.se.tardis.task.impl;

import java.util.Date;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

class TaskImpl implements Task {

	private final TaskId id;
	private String name;
	private String description;
	private final String owner;
	private Date dueDate;
	private ProjectId projectId;
	private TaskState taskState;

	public TaskImpl(TaskId id, ProjectId projectId, String TaskOwner) {
		this.id = id;
		this.owner = TaskOwner;
		this.projectId = projectId;
		this.taskState = TaskState.TODO;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDescription(String des) {
		this.description = des;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public TaskId getId() {
		return id;
	}

	@Override
	public Date getDueDate() {
		return dueDate;
	}

	@Override
	public void setDueDate(Date d) {
		this.dueDate = d;
	}

	@Override
	public String getProjectOwner() {
		return owner;
	}	
	
	
	@Override
	public ProjectId getProjectId() {
		// TODO Auto-generated method stub
		return projectId;
	}

	@Override
	public void setOwner(String member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTaskOwner() {
		// TODO Auto-generated method stub
		return owner;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskImpl other = (TaskImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskImpl [id=" + id + ",project id=" + projectId + ", name=" + name + ", owner=" + owner + ", dueDate="
				+ dueDate + ", state="+taskState+"]";
	}

	@Override
	public TaskState getTaskState() {
		return taskState;
	}

	@Override
	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
		
	}


}
