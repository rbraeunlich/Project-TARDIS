package kr.ac.kaist.se.tardis.project;

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
import kr.ac.kaist.se.tardis.project.api.ProjectRepository;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

@Configuration
@ComponentScan
public class TestConfig {

	@Bean
	public ProjectRepository createProjectRepository() {
		return new ProjectRepository() {

			private Set<ProjectImpl> projects = new HashSet<>();

			@Override
			public <S extends ProjectImpl> S save(S entity) {
				projects.add(entity);
				return entity;
			}

			@Override
			public ProjectImpl findOne(ProjectId id) {
				Optional<ProjectImpl> findAny = projects.stream().filter(p -> id.equals(p.getId())).findAny();
				if (findAny.isPresent()) {
					return findAny.get();
				} else {
					return null;
				}
			}

			@Override
			public boolean exists(ProjectId id) {
				return projects.stream().map(p -> p.getId()).filter(i -> id.equals(i)).findAny().isPresent();
			}

			@Override
			public void deleteAll() {
				projects.clear();
			}

			@Override
			public void delete(Iterable<? extends ProjectImpl> entities) {

			}

			@Override
			public void delete(ProjectImpl entity) {
				projects.remove(entity);
			}

			@Override
			public void delete(ProjectId id) {

			}

			@Override
			public long count() {
				return 0;
			}

			@Override
			public Page<ProjectImpl> findAll(Pageable pageable) {
				return null;
			}

			@Override
			public <S extends ProjectImpl> S saveAndFlush(S entity) {
				projects.add(entity);
				return entity;
			}

			@Override
			public <S extends ProjectImpl> List<S> save(Iterable<S> entities) {
				return null;
			}

			@Override
			public ProjectImpl getOne(ProjectId id) {
				return null;
			}

			@Override
			public void flush() {

			}

			@Override
			public List<ProjectImpl> findAll(Iterable<ProjectId> ids) {
				return null;
			}

			@Override
			public List<ProjectImpl> findAll(Sort sort) {
				return null;
			}

			@Override
			public List<ProjectImpl> findAll() {
				return new ArrayList<>(projects);
			}

			@Override
			public void deleteInBatch(Iterable<ProjectImpl> entities) {

			}

			@Override
			public void deleteAllInBatch() {

			}

			@Override
			public Set<Project> findProjectsByName(String name) {
				return projects.stream().filter(p -> name.equals(p.getName())).collect(Collectors.toSet());
			}
		};
	}
}
