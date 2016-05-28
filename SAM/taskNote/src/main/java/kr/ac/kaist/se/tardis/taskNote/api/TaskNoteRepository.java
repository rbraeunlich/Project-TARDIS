package kr.ac.kaist.se.tardis.taskNote.api;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public interface TaskNoteRepository extends JpaRepository<TaskNote, TaskNoteId> {

	Set<TaskNote> findTaskNotesByTask(TaskImpl id);

}
