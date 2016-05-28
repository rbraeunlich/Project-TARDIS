package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;

import javax.persistence.Entity;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

@Entity
public class Comment extends TaskNote {
	String comment;
	
	Comment(){
		super();
	}

	public Comment(TaskNoteId id, Task task, String author, Date writeDate, String comment) {
		super(id, task, author, writeDate);
		this.comment = comment;
	}

	public String getContent() {
		return comment;
	}

	public void setContnet(String comment) {
		this.comment = comment;
	}

}
