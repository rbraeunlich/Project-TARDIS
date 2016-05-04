package kr.ac.kaist.se.tardis.project.api;

import java.util.Set;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface ProjectService {

	Project createProject();
	
	Set<Project> findProjectsForUser(String userId);
	
	Project findProjectByName(String name);
	
	Project findProjectById(ProjectId id);
	
	void saveProject(Project p);
	
}
