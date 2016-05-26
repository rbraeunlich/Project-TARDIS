package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.CreateTaskNoteForm;
import kr.ac.kaist.se.tardis.web.validator.SetTaskFormValidator;
import kr.ac.kaist.se.tardis.web.validator.TaskNoteFormValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class TaskDetailController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TaskNoteService taskNoteService;
	@Autowired
	private TaskNoteFormValidator validator;

	private void fillModel(Model model, UserDetails user, TaskId id) {
		Task task = taskService.findTaskById(id).get();
		task.setProjectName(projectService.findProjectById(task.getProjectId()).get().getName());
		
		Set<TaskNote> taskNotes = taskNoteService.findtaskNoteByTaskId(id);
		List<TaskNote> tNs =new ArrayList<TaskNote>(taskNotes);
		Collections.sort(tNs, new Comparator<TaskNote>() {
		    public int compare(TaskNote o1, TaskNote o2) {
		    	
		        return o1.getWriteDate().compareTo(o2.getWriteDate());
		    }
		});
		
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("task", task);
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
		model.addAttribute("taskNoteList", tNs);
	}
	
	
	public String taskNotePage(Model model, CreateTaskForm form,
			String taskId,  UserDetails user,
			CreateTaskNoteForm createTaskNoteForm, BindingResult bindingResult){
		
		if (createTaskNoteForm.getComment() != null || createTaskNoteForm.getContribution() != null
				|| createTaskNoteForm.getProgress() != null)
			validator.validate(createTaskNoteForm, bindingResult);


		TaskId id = TaskIdFactory.valueOf(taskId);

		fillModel(model, user, id);
		
		return "taskdetail";
	}
	
	@RequestMapping(value = { "/taskDetail" }, method = RequestMethod.POST)
	public String taskNotePagePostmethod(Model model, CreateTaskForm form,
			@RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user,
			CreateTaskNoteForm createTaskNoteForm, BindingResult bindingResult) {
		return taskNotePage(model,form,taskId,user,createTaskNoteForm,bindingResult);
	}

	@RequestMapping(value = { "/taskDetail" }, method = RequestMethod.GET)
	public String taskNotePageGetmethod(Model model, CreateTaskForm form,
			@RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user,
			CreateTaskNoteForm createTaskNoteForm, BindingResult bindingResult) {
		return taskNotePage(model,form,taskId,user,createTaskNoteForm,bindingResult);
	}

	
	
	@RequestMapping(value = { "/addtask" }, method = RequestMethod.POST)
	public String addTask(Model model, CreateTaskForm form,
			@RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user,
			CreateTaskNoteForm createTaskNoteForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		validator.validate(createTaskNoteForm, bindingResult);

		TaskId id = TaskIdFactory.valueOf(taskId);
		fillModel(model, user, id);

		if (bindingResult.hasErrors()) {
			//return "forward:taskDetail";
			return "taskDetail";
		}
		TaskId taskid = TaskIdFactory.valueOf(taskId);
		if (createTaskNoteForm.getComment() != null &&createTaskNoteForm.getComment().length()!=0) {
			TaskNote newTaskNote = taskNoteService.createComment(taskid, String.valueOf(user.getUsername()), new Date(),
					createTaskNoteForm.getComment());
			taskNoteService.saveTaskNote(newTaskNote);
		}

		if (createTaskNoteForm.getContribution() != null || createTaskNoteForm.getProgress() != null) {
			if (createTaskNoteForm.getContribution().length() != 0 || createTaskNoteForm.getProgress().length() != 0) {
				TaskNote newTaskNote = taskNoteService.createContribution(taskid, String.valueOf(user.getUsername()),
						new Date(), Integer.parseInt(createTaskNoteForm.getProgress()),
						Integer.parseInt(createTaskNoteForm.getContribution()));
				taskNoteService.saveTaskNote(newTaskNote);
			}
		}
		redirectAttributes.addAttribute("taskId", createTaskNoteForm.getTaskId());
		return "redirect:taskDetail";
	}
}
