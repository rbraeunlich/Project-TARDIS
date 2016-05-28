package kr.ac.kaist.se.tardis.project.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectRepository;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository repo;

	@Override
	public Project createProject(String owner) {
		ProjectId projectId = ProjectIdFactory.generateProjectId();
		Project p = new ProjectImpl(projectId, owner);
		return p;
	}

	@Override
	public Set<Project> findProjectsForUser(String username) {
		return repo.findAll().stream()
				.filter(p -> (p.getProjectOwner().equals(username) || p.getProjectMembers().contains(username)))
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Project> findProjectByName(String name) {
		return repo.findProjectsByName(name);
	}

	@Override
	public Optional<Project> findProjectById(ProjectId id) {
		return Optional.ofNullable(repo.findOne(id));
	}

	@Override
	public void saveProject(Project p) {
		repo.save((ProjectImpl) p);
	}

	@Override
	public void deleteProject(Project p) {
		repo.delete((ProjectImpl) p);
	}

	@Override
	public Set<Project> getAllProjects() {
		return new HashSet<>(repo.findAll());
	}

}
