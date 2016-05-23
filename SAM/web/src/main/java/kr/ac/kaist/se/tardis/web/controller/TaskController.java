package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.SetTaskForm;
import kr.ac.kaist.se.tardis.web.validator.SetTaskFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by insanebear on 5/16/16.
 */
@Controller
public class TaskController {
	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectService projectService;
	@Autowired
	private SetTaskFormValidator validator;

	private void fillModel(Model model, UserDetails user, TaskId taskId, ProjectId projectId) {

		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("task", taskService.findTaskById(taskId).get());
		model.addAttribute("project", projectService.findProjectById(projectId).get());
		model.addAttribute("taskList", taskService.findTaskByProjectId(projectId));
	}

	@RequestMapping(value = { "/taskview" }, method = RequestMethod.GET)
	public String showTaskInfoView(Model model, @RequestParam(name = "taskId", required = true) String taskId,
			SetTaskForm form, @AuthenticationPrincipal UserDetails user) {
		TaskId id = TaskIdFactory.valueOf(taskId);
		fillModel(model, user, id, taskService.findTaskById(id).get().getProjectId());
		Optional<Task> optional = taskService.findTaskById(id);
		if (optional.isPresent()) {// Task is present in optional
			model.addAttribute("task", optional.get());
		} else {
			// TODO error case
		}
		return "tasksettingview";

	}

	@RequestMapping(value = { "/taskview" }, method = RequestMethod.POST)

	public String taskInfoView(Model model, @RequestParam(name = "taskId", required = true) String taskId,
			@AuthenticationPrincipal UserDetails user, @Valid SetTaskForm setTaskForm, BindingResult bindingResult) {
		// show task information on task setting page

		if (setTaskForm.getTaskName() != null || setTaskForm.getOwner() != null || setTaskForm.getDueDate() != null)
			validator.validate(setTaskForm, bindingResult);

		TaskId id = TaskIdFactory.valueOf(taskId);
		fillModel(model, user, id, taskService.findTaskById(id).get().getProjectId());

		Optional<Task> optional = taskService.findTaskById(id);

		if (optional.isPresent()) {// Task is present in optional
			model.addAttribute("task", optional.get());
		} else {
			// TODO error case

		}

		return "tasksettingview";
	}

	@RequestMapping(value = { "/taskchange" }, method = RequestMethod.POST)
	public String taskChange(Model model, CreateTaskForm form,
			@RequestParam(name = "taskId", required = true) String taskId, @AuthenticationPrincipal UserDetails user,
			@Valid SetTaskForm setTaskForm, BindingResult bindingResult,
			@RequestParam(name = "projectId", required = true) String projectId) {
		TaskId id = TaskIdFactory.valueOf(taskId);
		setTaskForm.setProjectId(projectId);

		validator.validate(setTaskForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "forward:taskview";
		}
			Optional<Task> optional = taskService.findTaskById(id);

			if (optional.isPresent()) {
				Task changedTask = optional.get();

				// TODO update features

				changedTask.setName(setTaskForm.getTaskName());
				changedTask.setDescription(setTaskForm.getTaskDescription());
				changedTask.setOwner(setTaskForm.getOwner());
				Date tmp;
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					tmp = format.parse(form.getDueDate());
					changedTask.setDueDate(tmp);
				} catch (ParseException e) {
				}
				taskService.saveTask(changedTask);

				fillModel(model, user, id, taskService.findTaskById(id).get().getProjectId());

			} else {
				// TODO error case
			}
		return "kanbanBoard";
	}

}
