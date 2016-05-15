package kr.ac.kaist.se.tardis.task.impl.id;

import java.util.UUID;

public class TaskIdFactory {

	public static TaskId generateTaskId() {
		UUID randomUUID = UUID.randomUUID();
		return new TaskId(randomUUID.toString());
	}

}
