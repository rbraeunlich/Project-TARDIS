package kr.ac.kaist.se.tardis.taskNote.api;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public interface TaskNoteService {

	/**
	 * Creates a new taskNote, which has no values set except the id. This
	 * {@link taskNote} is NOT persisted yet. So make sure to call
	 * {@link #savetaskNote(taskNote)} later.
	 * 
	 * @return
	 */
	TaskNote createComment(Task task, String taskNoteOwner, Date writeDate, String comment);

	TaskNote createContribution(Task task, String taskNoteOwner, Date writeDate, int progress, int contributioin);

	/**
	 * Returns a set of taskNotes for the specified user. If the user has no
	 * taskNotes an empty set is being returned.
	 * 
	 * @param userId
	 * @return
	 */

	Optional<TaskNote> findtaskNoteById(TaskNoteId id);

	/**
	 * Returns the taskNote that has the given TaskId
	 * 
	 * @param id
	 * @return
	 */
	Set<TaskNote> findtaskNoteByTaskId(TaskId id);

	/**
	 * Persists the TaskNote and saves all changes made.
	 * 
	 * @param t
	 */
	void saveTaskNote(TaskNote t);

	/**
	 * Deletes the TaskNote. If the Task was never persisted it does nothing.
	 * 
	 * @param t
	 */
	void deleteTaskNote(TaskNote t);

}
