package kr.ac.kaist.se.tardis.task.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.TaskJobInfo;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

@Entity(name = "task")
public class TaskImpl implements Task {

	@EmbeddedId
	private TaskId id;
	private String name;
	private String description;
	private String owner;
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@ManyToOne(targetEntity = ProjectImpl.class)
	@JoinColumn(name = "projectid", referencedColumnName="id")
	private Project project;

	@Enumerated(EnumType.STRING)
	private TaskState taskState;

	@Transient
	private int taskProgress;

	@OneToMany(targetEntity = TaskJobInfo.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "taskid", referencedColumnName = "id")
	private Set<JobInfo> jobInfos = new HashSet<>();
	private Integer key;

	TaskImpl() {
	}

	public TaskImpl(TaskId id, Project project, String taskOwner) {
		this.id = id;
		this.owner = taskOwner;
		this.project = project;
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
	public Project getProject() {
		return project;
	}
	
	@Override
	public ProjectId getProjectId() {
		return project.getId();
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
	public Integer getKey() {
		return key;
	}

	@Override
	public void setKey(Integer key) {
		this.key = key;
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
		return "TaskImpl [id=" + id + ",project id=" + project + ", name=" + name + ", owner=" + owner + ", dueDate="
				+ dueDate + ", state=" + taskState + "]";
	}

}
