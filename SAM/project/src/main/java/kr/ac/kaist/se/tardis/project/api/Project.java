package kr.ac.kaist.se.tardis.project.api;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface Project {

	void setName(String name);

	ProjectId getId();

	String getName();

}
