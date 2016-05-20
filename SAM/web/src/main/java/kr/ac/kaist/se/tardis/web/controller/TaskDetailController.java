package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;

@Controller
public class TaskDetailController {

	@Autowired
	private TaskService taskService;
	
	private void fillModel(Model model, UserDetails user, TaskId id) {
		
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("task", taskService.findTaskById(id).get());
	}
	
	@RequestMapping(value = { "/taskdetail" }, method = RequestMethod.GET)
	public String projectManagementpage(Model model, CreateTaskForm form, @RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user) {
		TaskId id = TaskIdFactory.valueOf(taskId);
		
		fillModel(model, user, id);
		return "taskdetail";
	}
	
}
