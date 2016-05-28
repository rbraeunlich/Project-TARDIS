package kr.ac.kaist.se.tardis.taskNote.api;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;

public interface TaskNoteRepository extends JpaRepository<TaskNote, TaskNoteId> {

}
