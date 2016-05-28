package kr.ac.kaist.se.tardis.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskRepository;
import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;

@Configuration
@ComponentScan
public class TestConfig {

	@Bean
	public ProjectService createMockProjectService(){
		return new ProjectService() {
			private HashSet<Project> projects = new HashSet<>();
			
			@Override
			public void saveProject(Project p) {
				projects.add(p);
			}
			
			@Override
			public Set<Project> getAllProjects() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Set<Project> findProjectsForUser(String username) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Set<Project> findProjectByName(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Optional<Project> findProjectById(ProjectId id) {
				return projects.stream().filter(p -> id.equals(p.getId())).findAny();
			}
			
			@Override
			public void deleteProject(Project p) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Project createProject(String owner) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
	public TaskRepository creaetTaskRepository() {
		return new TaskRepository() {

			private HashSet<TaskImpl> tasks = new HashSet<>();

			@Override
			public <S extends TaskImpl> S save(S entity) {
				tasks.add(entity);
				return entity;
			}

			@Override
			public TaskImpl findOne(TaskId id) {
				Optional<TaskImpl> findAny = tasks.stream().filter(t -> id.equals(t.getId())).findAny();
				if (findAny.isPresent()) {
					return findAny.get();
				}
				return null;
			}

			@Override
			public boolean exists(TaskId id) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void deleteAll() {
				tasks.clear();
			}

			@Override
			public void delete(Iterable<? extends TaskImpl> entities) {
				// TODO Auto-generated method stub

			}

			@Override
			public void delete(TaskImpl entity) {
				tasks.remove(entity);
			}

			@Override
			public void delete(TaskId id) {
				// TODO Auto-generated method stub

			}

			@Override
			public long count() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Page<TaskImpl> findAll(Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <S extends TaskImpl> S saveAndFlush(S entity) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <S extends TaskImpl> List<S> save(Iterable<S> entities) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public TaskImpl getOne(TaskId id) {
				Optional<TaskImpl> findAny = tasks.stream().filter(t -> id.equals(t.getId())).findAny();
				if (findAny.isPresent()) {
					return findAny.get();
				}
				return null;
			}

			@Override
			public void flush() {
				// TODO Auto-generated method stub

			}

			@Override
			public List<TaskImpl> findAll(Iterable<TaskId> ids) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<TaskImpl> findAll(Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<TaskImpl> findAll() {
				return new ArrayList<>(tasks);
			}

			@Override
			public void deleteInBatch(Iterable<TaskImpl> entities) {
				// TODO Auto-generated method stub

			}

			@Override
			public void deleteAllInBatch() {
				// TODO Auto-generated method stub

			}

			@Override
			public Set<Task> findTasksByOwner(String userId) {
				return tasks.stream().filter(t -> (t.getOwner().equals(userId))).collect(Collectors.toSet());
			}

			@Override
			public Set<Task> findTasksByName(String name) {
				return tasks.stream().filter(t -> name.equals(t.getName())).collect(Collectors.toSet());
			}

			@Override
			public Set<Task> findTaskByProject(ProjectImpl project) {
				return tasks.stream().filter(t -> project.equals(t.getProject())).collect(Collectors.toSet());
			}
		};
	}

}
