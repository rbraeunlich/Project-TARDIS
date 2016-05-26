package kr.ac.kaist.se.tardis.taskNote.api;

import java.util.Date;

import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.impl.Comment;
import kr.ac.kaist.se.tardis.taskNote.impl.Contribution;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public abstract class TaskNote {
	
	private TaskNoteId id;
	private TaskId taskId;
	private String author;
	private Date writeDate;
	
	protected TaskNote(TaskNoteId id,TaskId taskId, String author, Date writeDate){
		this.id =id;
		this.taskId = taskId;
		this.author = author;
		this.writeDate = writeDate;
	}

	
	public abstract String getContent();
	
	public TaskNoteId getId(){
		return id;
	}
			
	public TaskId getTaskId() {
		return taskId;
	}


	public String getAuthor() {
		return author;
	}

	public Date getWriteDate() {
		return writeDate;
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
		
		return "TaskNote [id=" + id + ",type=" + type +",task id=" + taskId +  ", owner=" + author + ", writeDate="
				+ writeDate + ", content=" + getContent() + "]";
	}

}
