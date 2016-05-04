package kr.ac.kaist.se.tardis.project.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Override
	public Project createProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Project> findProjectsForUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project findProjectById(ProjectId id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProject(Project p) {
		// TODO Auto-generated method stub
		
	}

}
