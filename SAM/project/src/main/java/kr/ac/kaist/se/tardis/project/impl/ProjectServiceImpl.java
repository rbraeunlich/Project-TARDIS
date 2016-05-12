package kr.ac.kaist.se.tardis.project.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

@Service
public class ProjectServiceImpl implements ProjectService {

	// FIXME replace this set with a DTO later
	private Set<Project> projects = new HashSet<>();

	@Override
	public Project createProject(String owner) {
		ProjectId projectId = ProjectIdFactory.generateProjectId();
		Project p = new ProjectImpl(projectId, owner);
		return p;
	}

	@Override
	public Set<Project> findProjectsForUser(String userId) {
		return projects
				.stream()
				.filter(p -> (p.getProjectOwner().equals(userId) || p
						.getProjectMembers().contains(userId)))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Project> findProjectByName(String name) {
		return projects.stream().filter(p -> name.equals(p.getName()))
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<Project> findProjectById(ProjectId id) {
		return projects.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	@Override
	public void saveProject(Project p) {
		projects.add(p);
	}

	@Override
	public void deleteProject(Project p) {
		projects.remove(p);
	}

	@Override
	public Set<Project> getAllProjects() {
		return new HashSet<>(projects);
	}

}
