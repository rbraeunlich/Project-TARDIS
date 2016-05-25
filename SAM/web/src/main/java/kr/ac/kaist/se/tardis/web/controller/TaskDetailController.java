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

import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.CreateTaskNoteForm;

import java.util.Set;

@Controller
public class TaskDetailController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private NotificationService notificationService;
//	@Autowired
//	private TaskNoteService taskNoteService;
//	
	private void fillModel(Model model, UserDetails user, TaskId id) {
		Task task = null;
		Set<Task> tasks = taskService.findTasksForUser(String.valueOf(user.getUsername()));

		for (Task t : tasks) {
			if(t.equals(taskService.findTaskById(id).get())){
				t.setProjectName(projectService.findProjectById(t.getProjectId()).get().getName());
					task = t;
					break;
			}
		}
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("task", task);
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
	}
	
	@RequestMapping(value = { "/taskdetail" }, method = RequestMethod.POST)
	public String taskNotePage(Model model, CreateTaskForm form, @RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user, CreateTaskNoteForm createTaskNoteForm) {
		TaskId id = TaskIdFactory.valueOf(taskId);
		
		fillModel(model, user, id);
		return "taskdetail";
	}
	
	@RequestMapping(value = { "/addtask" }, method = RequestMethod.POST)
	public String addTask(Model model, CreateTaskForm form, @RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user, CreateTaskNoteForm createTaskNoteForm
			, BindingResult bindingResult) {
		TaskId id = TaskIdFactory.valueOf(taskId);		
		fillModel(model, user, id);
		
		
		
		return "taskdetail";
	}
}
