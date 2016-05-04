package kr.ac.kaist.se.tardis.project.impl.id;

import java.util.UUID;

public class ProjectIdFactory {

	public static ProjectId generateProjectId() {
		UUID randomUUID = UUID.randomUUID();
		return new ProjectId(randomUUID.toString());
	}

}
