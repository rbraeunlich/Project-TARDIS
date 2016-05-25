package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;

import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public class Contribution extends TaskNote {
	int progress;
	int contribution;

	public Contribution(TaskNoteId id, TaskId taskId, String taskNoteOwner, Date writeDate,int progress, int contributioin) {
		super(id, taskId, taskNoteOwner, writeDate);
		this.progress=progress;
		this.contribution=contributioin;
	}
	public String getContent(){
		return "Progress: " + progress + ", Contribution: "+contribution;
	}
	public void setContribution(int contribution){
		this.contribution = contribution;
	}
	public void setProgress(int progress){
		this.progress = progress;
	}
}
