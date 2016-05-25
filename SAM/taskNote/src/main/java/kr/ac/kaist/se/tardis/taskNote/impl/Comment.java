package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;

import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public class Comment extends TaskNote {
	String comment;

	public Comment(TaskNoteId id, TaskId taskId, String taskNoteOwner, Date writeDate,String comment) {
		super(id, taskId, taskNoteOwner, writeDate);
		this.comment = comment;
	}
	public String getContent(){
		return comment;
	}
	public void setContnet(String comment){
		this.comment = comment;
	}

}
