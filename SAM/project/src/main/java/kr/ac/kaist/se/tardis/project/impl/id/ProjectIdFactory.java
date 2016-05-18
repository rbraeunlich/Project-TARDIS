package kr.ac.kaist.se.tardis.project.impl.id;

import java.util.UUID;

public class ProjectIdFactory {

	public static ProjectId generateProjectId() {
		UUID randomUUID = UUID.randomUUID();
		return new ProjectId(randomUUID.toString());
	}

	public static ProjectId valueOf(String projectIdParameter) {
		//validation
		UUID.fromString(projectIdParameter);
		return new ProjectId(projectIdParameter);
	}

}
