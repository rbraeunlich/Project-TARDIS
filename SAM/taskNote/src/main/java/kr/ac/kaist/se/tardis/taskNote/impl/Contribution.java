package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;

import javax.persistence.Entity;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

@Entity
public class Contribution extends TaskNote {
	private Integer progress;
	private Integer contribution;

	Contribution() {
		super();
	}

	public Contribution(TaskNoteId id, Task task, String author, Date writeDate, Integer progress, Integer contribution) {
		super(id, task, author, writeDate);
		this.progress = progress;
		this.contribution = contribution;
	}

	public String getContent() {
		if (progress != null && contribution != null) {
			return "Progress: " + progress + ", Contribution: " + contribution + " from " + getAuthor();
		} else if( progress != null){
			return "Progress: " + progress + " from " + getAuthor();
		} else if (contribution != null){
			return "Contribution: " + contribution + " from " + getAuthor();
		} else{
			return "Commit from " + getAuthor();
		}
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
