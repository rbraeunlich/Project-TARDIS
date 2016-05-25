package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.NotNull;

public class CreateTaskNoteForm {
	public static final String SHORT_COMMENT = "Comment must contain at least two characters";
	public static final String EMPTY_FORM = "There are no contents!";
	public static final String EMPTY_PROGRESS_OR_CONTRIBUTION = "Progress and Contribution are pair."; 
	public static final String PROGRESS_MUST_NUMBER = "Progress is a integer(0~100)";
	public static final String CONTRIBUTION_MUST_NUMBER = "Contribution is a integer(>=0)";
	
	private String comment;
	private String progress;
	private String contribution;
	
	@NotNull
	private String taskId;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getContribution() {
		return contribution;
	}
	public void setContribution(String contribution) {
		this.contribution = contribution;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	

}
