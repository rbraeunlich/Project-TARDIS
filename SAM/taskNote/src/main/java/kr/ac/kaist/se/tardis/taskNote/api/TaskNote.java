package kr.ac.kaist.se.tardis.taskNote.api;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.impl.Comment;
import kr.ac.kaist.se.tardis.taskNote.impl.Contribution;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

@Entity(name="tasknote")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class TaskNote implements Comparable<TaskNote>{
	
	@EmbeddedId
	private TaskNoteId id;
	
	@ManyToOne(targetEntity=TaskImpl.class)
	@JoinColumn(name = "taskid", referencedColumnName="id")
	private Task task;
	private String author;
	@Temporal(TemporalType.TIMESTAMP)
	private Date writeDate;
	
	protected TaskNote(){}
	
	protected TaskNote(TaskNoteId id, Task task, String author, Date writeDate){
		this.id =id;
		this.task = task;
		this.author = author;
		this.writeDate = writeDate;
	}

	
	public abstract String getContent();
	
	public TaskNoteId getId(){
		return id;
	}
			
	public TaskId getTaskId() {
		return task.getId();
	}


	public String getAuthor() {
		return author;
	}

	public Date getWriteDate() {
		return writeDate;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	@Override
	public int compareTo(TaskNote o) {
		return o.getWriteDate().compareTo(getWriteDate());
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
		TaskNote other = (TaskNote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String type = "";
		if(this instanceof Comment)
			type = "Comment";
		else if(this instanceof Contribution)
			type = "Contribution";
		
		return "TaskNote [id=" + id + ",type=" + type +",task id=" + task.getId() +  ", owner=" + author + ", writeDate="
				+ writeDate + ", content=" + getContent() + "]";
	}

}
