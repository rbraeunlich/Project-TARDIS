package kr.ac.kaist.se.tardis.users.impl.security;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@Component
public class SamUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserImpl foundUser = userRepo.findOne(username);
		if (foundUser == null) {
			return new User("unknown", "", false, false, false, false, Collections.emptySet());
		}
		return new User(username, foundUser.getPassword(), true, true, true, true, Collections.emptySet());
	}

	public boolean isUserProjectOwner(Authentication authentication, HttpServletRequest request) {
		if (!authentication.isAuthenticated()) {
			return false;
		}
		Optional<Project> project = findProject(request);
		if (!project.isPresent()) {
			return false;
		}
		String projectOwner = project.get().getProjectOwner();
		return projectOwner.equals(authentication.getName());
	}

	public boolean isUserPartOfProject(Authentication authentication, HttpServletRequest request) {
		if (!authentication.isAuthenticated()) {
			return false;
		}
		Optional<Project> project = findProject(request);
		if (!project.isPresent()) {
			return false;
		}
		Set<String> projectMembers = project.get().getProjectMembers();
		String projectOwner = project.get().getProjectOwner();
		return projectMembers.contains(authentication.getName()) || projectOwner.equals(authentication.getName());
	}

	public boolean hasUserTask(Authentication authentication, HttpServletRequest request) {
		if (!authentication.isAuthenticated()) {
			return false;
		}
		String taskIdParameter = request.getParameter("taskId");
		Optional<Task> task = taskService.findTaskById(TaskIdFactory.valueOf(taskIdParameter));
		if (!task.isPresent()) {
			return false;
		}
		return task.get().getTaskOwner().equals(authentication.getName());
	}

	public boolean isUserAllowedToChangeTask(Authentication authentication, HttpServletRequest request) {
		if (!authentication.isAuthenticated()) {
			return false;
		}
		String taskIdParameter = request.getParameter("taskId");
		Optional<Task> task = taskService.findTaskById(TaskIdFactory.valueOf(taskIdParameter));
		if (!task.isPresent()) {
			return false;
		}
		Optional<Project> project = projectService.findProjectById(task.get().getProjectId());
		if (!project.isPresent()) {
			// this actually shouldn't happen since a task without a project
			// cannot exist
			return false;
		}
		boolean isUserProjectOwner = project.get().getProjectOwner().equals(authentication.getName());
		return isUserProjectOwner || hasUserTask(authentication, request);
	}
	
	public boolean isUserAllowedToSeeTask(Authentication authentication, HttpServletRequest request){
		if (!authentication.isAuthenticated()) {
			return false;
		}
		String taskIdParameter = request.getParameter("taskId");
		Optional<Task> task = taskService.findTaskById(TaskIdFactory.valueOf(taskIdParameter));
		if (!task.isPresent()) {
			return false;
		}
		Optional<Project> project = projectService.findProjectById(task.get().getProjectId());
		if (!project.isPresent()) {
			// this actually shouldn't happen since a task without a project
			// cannot exist
			return false;
		}
		Set<String> projectMembers = project.get().getProjectMembers();
		String projectOwner = project.get().getProjectOwner();
		return projectMembers.contains(authentication.getName()) || projectOwner.equals(authentication.getName());
	}

	private Optional<Project> findProject(HttpServletRequest request) {
		String projectIdParameter = request.getParameter("projectId");
		Optional<Project> project = projectService.findProjectById(ProjectIdFactory.valueOf(projectIdParameter));
		return project;
	}
}
