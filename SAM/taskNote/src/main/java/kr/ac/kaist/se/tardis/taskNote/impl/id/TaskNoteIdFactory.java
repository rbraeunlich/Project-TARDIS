package kr.ac.kaist.se.tardis.taskNote.impl.id;

import java.util.UUID;

public class TaskNoteIdFactory {

	public static TaskNoteId generateTaskNoteId() {
		UUID randomUUID = UUID.randomUUID();
		return new TaskNoteId(randomUUID.toString());
	}

	public static TaskNoteId valueOf(String taskNoteId){
		//validate taskId
		UUID.fromString(taskNoteId);
		//translate string to TaskId
		return new TaskNoteId(taskNoteId);
	}

}
