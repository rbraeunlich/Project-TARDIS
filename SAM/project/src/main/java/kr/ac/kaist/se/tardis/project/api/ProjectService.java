package kr.ac.kaist.se.tardis.project.api;

import java.util.Optional;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface ProjectService {

	/**
	 * Creates a new project, which has no values set except the id. This
	 * {@link Project} is NOT persisted yet. So make sure to call
	 * {@link #saveProject(Project)} later.
	 * 
	 * @return
	 */
	Project createProject();

	/**
	 * Returns a set of projects for the specified user. If the user has no
	 * projects an empty set is being returned.
	 * 
	 * @param userId
	 * @return
	 */
	Set<Project> findProjectsForUser(String userId);

	/**
	 * Finds projects that <b>exactly</b> match the given name.
	 * 
	 * @param name
	 * @return
	 */

	Set<Project> findProjectByName(String name);

	/**
	 * Returns the project that has the given {@link ProjectId} or null, if no project is present.
	 * @param id
	 * @return
	 */
	Optional<Project> findProjectById(ProjectId id);

	/**
	 * Persists the project and saves all changes made.
	 * @param p
	 */
	void saveProject(Project p);

	/**
	 * Deletes the project. If the project was never persisted it does nothing.
	 * @param p
	 */
	void deleteProject(Project p);

	Set<Project> getAllProjects();
}
