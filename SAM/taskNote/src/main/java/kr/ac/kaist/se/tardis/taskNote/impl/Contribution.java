package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;

import javax.persistence.Entity;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

@Entity
public class Contribution extends TaskNote {
	int progress;
	int contribution;
	
	Contribution(){
		super();
	}

	public Contribution(TaskNoteId id, Task task, String author, Date writeDate, int progress, int contributioin) {
		super(id, task, author, writeDate);
		this.progress = progress;
		this.contribution = contributioin;
	}

	public String getContent() {
		return "Progress: " + progress + ", Contribution: " + contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
