package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteService;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteIdFactory;
@Service
public class TaskNoteServiceImpl implements TaskNoteService {
	
	// FIXME replace this set with a DTO later
	private Set<TaskNote> taskNotes = new HashSet<>();
	
	@Override
	public TaskNote createComment(TaskId taskId, String taskNoteOwner, Date writeDate, String comment) {
		TaskNoteId taskNoteId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNote t = new Comment(taskNoteId, taskId, taskNoteOwner, writeDate, comment);
		return t;
	}

	@Override
	public TaskNote createContribution(TaskId taskId, String taskNoteOwner, Date writeDate, int progress,
			int contributioin) {
		TaskNoteId taskNoteId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNote t = new Contribution(taskNoteId, taskId, taskNoteOwner, writeDate, progress, contributioin);
		return t;
	}

	@Override
	public Optional<TaskNote> findtaskNoteById(TaskNoteId id) {
		return taskNotes.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	@Override
	public Set<TaskNote> findtaskNoteByTaskId(TaskId id) {
		return taskNotes.stream().filter(p -> id.equals(p.getTaskId()))
				.collect(Collectors.toSet());
	}

	@Override
	public void saveTaskNote(TaskNote t) {
		// TODO Auto-generated method stub
		taskNotes.add(t);
	}

	@Override
	public void deleteTaskNote(TaskNote t) {
		// TODO Auto-generated method stub
		taskNotes.remove(t);
	}

}
