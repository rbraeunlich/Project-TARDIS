package kr.ac.kaist.se.tardis.task.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

class TaskImpl implements Task {

	private final TaskId id;
	private String name;
	private String description;
	private String owner;
	private Date dueDate;
	private ProjectId projectId;
	private TaskState taskState;
	private int taskProgress;
	private String projectName;
	private Set<JobInfo> jobInfos = new HashSet<>();

	public TaskImpl(TaskId id, ProjectId projectId, String taskOwner) {
		this.id = id;
		this.owner = taskOwner;
		this.projectId = projectId;
		this.taskState = TaskState.TODO;
		projectName = "";
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public ProjectId getProjectId() {
		return projectId;
	}

	@Override
	public void setOwner(String member) {
		owner = member;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public TaskState getTaskState() {
		return taskState;
	}

	@Override
	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;

	}

	@Override
	public int getTaskProgress() {
		return taskProgress;
	}

	@Override
	public void setTaskProgress(int taskProgress) {
		this.taskProgress = taskProgress;
	}

	@Override
	public Set<JobInfo> getAllJobInfos() {
		return jobInfos;
	}

	@Override
	public void removeJobInfo(JobInfo jobInfo) {
		jobInfos.remove(jobInfo);
	}

	@Override
	public void addJobInfo(JobInfo jobInfo) {
		jobInfos.add(jobInfo);
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
				+ dueDate + ", state=" + taskState + "]";
	}
}
