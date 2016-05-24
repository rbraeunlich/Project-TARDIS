package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.validator.TaskFormValidator;
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

import kr.ac.kaist.se.tardis.task.api.*;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class KanbanBoardController {
	@Autowired
	private TaskFormValidator validator;
	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	private void fillModel(Model model, UserDetails user, ProjectId projectId) {

		model.addAttribute("username", String.valueOf(user.getUsername()));
		Set<Task> allTasks = taskService.findTaskByProjectId(projectId);
		model.addAttribute("todoTasks",
				allTasks.stream().filter(t -> TaskState.TODO.equals(t.getTaskState())).collect(Collectors.toSet()));
		model.addAttribute("inProgressTasks", allTasks.stream()
				.filter(t -> TaskState.INPROGRESS.equals(t.getTaskState())).collect(Collectors.toSet()));
		model.addAttribute("reviewTasks",
				allTasks.stream().filter(t -> TaskState.REVIEW.equals(t.getTaskState())).collect(Collectors.toSet()));
		model.addAttribute("doneTasks",
				allTasks.stream().filter(t -> TaskState.DONE.equals(t.getTaskState())).collect(Collectors.toSet()));
		model.addAttribute("project", projectService.findProjectById(projectId).get());
	}

	@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.GET)
	public String projectManagementpage(Model model, CreateTaskForm form,
			@RequestParam(name = "projectId", required = true) String projectId,
			@AuthenticationPrincipal UserDetails user) {
		ProjectId id = ProjectIdFactory.valueOf(projectId);

		fillModel(model, user, id);
		return "KanbanBoard";
	}

	@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.POST)
	public String taskCreated(Model model, @Valid CreateTaskForm form, BindingResult bindingResult,
			@AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes) {

		ProjectId id = ProjectIdFactory.valueOf(form.getProjectId());

		validator.validate(form, bindingResult);

		if (!bindingResult.hasErrors()) {

			Task task = taskService.createTask(String.valueOf(user.getUsername()), id);
			task.setDescription(form.getTaskDescription());
			task.setName(form.getTaskName());
			task.setOwner(form.getOwner());
			task.setTaskProgress(0);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			try {
				Date tmp;
				tmp = format.parse(form.getDueDate());
				task.setDueDate(tmp);
			} catch (ParseException e) {
			}

			taskService.saveTask(task);
			fillModel(model, user, id);
			redirectAttributes.addAttribute("projectId", form.getProjectId());
			return "redirect:kanbanboard";
		} else {
			fillModel(model, user, id);
			return "kanbanboard";
		}

	}

	@RequestMapping(value = { "/updatestaskstatus" }, method = RequestMethod.POST)
	public String updateTaskStatus(Model model, CreateTaskForm form,
			@RequestParam(name = "projectId", required = true) String projectId,
			@RequestParam(name = "taskId", required = true) String taskId,
			@RequestParam(name = "status", required = true) String status, 
			@AuthenticationPrincipal UserDetails user,
			RedirectAttributes redirectAttributes) {
		Optional<Task> optional = taskService.findTaskById(TaskIdFactory.valueOf(taskId));
		if (!optional.isPresent()) {
			// TODO error case
		}
		TaskState newState = TaskState.valueOf(status);
		Task task = optional.get();
		task.setTaskState(newState);
		taskService.saveTask(task);
		fillModel(model, user, ProjectIdFactory.valueOf(projectId));
		redirectAttributes.addAttribute("projectId", projectId);
		return "redirect:kanbanboard";
	}
}
