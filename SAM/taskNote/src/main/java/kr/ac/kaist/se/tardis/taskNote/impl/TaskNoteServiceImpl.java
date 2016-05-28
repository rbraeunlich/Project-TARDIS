package kr.ac.kaist.se.tardis.taskNote.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteRepository;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteService;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteIdFactory;

@Service
public class TaskNoteServiceImpl implements TaskNoteService {

	@Autowired
	private TaskNoteRepository repo;

	@Override
	public TaskNote createComment(Task task, String author, Date writeDate, String comment) {
		TaskNoteId taskNoteId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNote t = new Comment(taskNoteId, task, author, writeDate, comment);
		return t;
	}

	@Override
	public TaskNote createContribution(Task task, String author, Date writeDate, Integer progress,
			Integer contribution) {
		TaskNoteId taskNoteId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNote t = new Contribution(taskNoteId, task, author, writeDate, progress, contribution);
		return t;
	}

	@Override
	public Optional<TaskNote> findTaskNoteById(TaskNoteId id) {
		return Optional.of(repo.findOne(id));
	}

	@Override
	public Set<TaskNote> findTaskNotesByTaskId(TaskId id) {
		return repo.findTaskNotesByTask(id);
	}

	@Override
	public void saveTaskNote(TaskNote t) {
		repo.save(t);
	}

	@Override
	public void deleteTaskNote(TaskNote t) {
		repo.delete(t);
	}

}
