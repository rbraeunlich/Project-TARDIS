package kr.ac.kaist.se.tardis.task.api;

import java.util.Optional;
import java.util.Set;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;

public interface TaskService {

	/**
	 * Creates a new Task, which has no values set except the id. This
	 * {@link Task} is NOT persisted yet. So make sure to call
	 * {@link #saveTask(Task)} later.
	 * 
	 * @return
	 */
	Task createTask(String owner,ProjectId projectId);

	/**
	 * Returns a set of Tasks for the specified user. If the user has no
	 * Tasks an empty set is being returned.
	 * 
	 * @param userId
	 * @return
	 */
	Set<Task> findTasksForUser(String userId);

	/**
	 * Finds Tasks that <b>exactly</b> match the given name.
	 * 
	 * @param name
	 * @return
	 */

	Set<Task> findTaskByName(String name);

	/**
	 * Returns the Task that has the given {@link TaskId} or null, if no Task is present.
	 * @param id
	 * @return
	 */
	Optional<Task> findTaskById(TaskId id);
	
	/**
	 * Returns the Task that has the given ProjectId
	 * @param id
	 * @return
	 */
	Set<Task> findTaskByProjectId(ProjectId id);
	

	/**
	 * Persists the Task and saves all changes made.
	 * @param p
	 */
	void saveTask(Task p);

	/**
	 * Deletes the Task. If the Task was never persisted it does nothing.
	 * @param p
	 */
	void deleteTask(Task p);

	Set<Task> getAllTasks();
	
}
