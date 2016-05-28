package kr.ac.kaist.se.tardis.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

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

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.FormWithNotification;
import kr.ac.kaist.se.tardis.web.form.SetTaskForm;
import kr.ac.kaist.se.tardis.web.validator.SetTaskFormValidator;

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
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private SchedulerService schedulerService;

	private void fillModel(Model model, UserDetails user, TaskId taskId, ProjectId projectId) {

		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("task", taskService.findTaskById(taskId).get());
		model.addAttribute("project", projectService.findProjectById(projectId).get());
		model.addAttribute("taskList", taskService.findTaskByProjectId(projectId));
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
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

		validator.validate(setTaskForm, bindingResult);
		if(bindingResult.hasErrors()){
			return "tasksettingview";
		}

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
			@RequestParam(name = "projectId", required = true) String projectId,
			RedirectAttributes redirectAttributes) {
		TaskId id = TaskIdFactory.valueOf(taskId);
		setTaskForm.setProjectId(projectId);

		validator.validate(setTaskForm, bindingResult);

		if (bindingResult.hasErrors()) {
			fillModel(model, user, id, taskService.findTaskById(id).get().getProjectId());
			redirectAttributes.addAttribute("taskId", taskId);
			return "tasksettingview";
		}
		Optional<Task> optional = taskService.findTaskById(id);

		if (optional.isPresent()) {
			Task changedTask = optional.get();

			changedTask.setName(setTaskForm.getTaskName());
			changedTask.setDescription(setTaskForm.getTaskDescription());
			changedTask.setOwner(setTaskForm.getOwner());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date projectDueDate = null;
			try {
				String dueDate = setTaskForm.getDueDate();
				if (dueDate != null && !dueDate.trim().isEmpty()) {
					projectDueDate = dateFormat.parse(dueDate);
					changedTask.setDueDate(projectDueDate);
					changedTask = createAndDeleteJobsForTask(changedTask, setTaskForm, projectDueDate);
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			if(setTaskForm.getKey() != null){
				changedTask.setKey(setTaskForm.getKey());
			}
			taskService.saveTask(changedTask);

			fillModel(model, user, id, taskService.findTaskById(id).get().getProjectId());

		} else {
			// TODO error case
		}

		redirectAttributes.addAttribute("taskId", taskId);
		return "redirect:taskDetail";
	}

	/**
	 * Depending on how the checkboxes were changed, the notifications for a
	 * project must either be added or removed.
	 * 
	 * @param changedTask
	 * @param setProjectForm
	 * @param projectDueDate
	 * @return
	 */
	private Task createAndDeleteJobsForTask(Task changedTask, FormWithNotification setProjectForm,
			Date projectDueDate) {
		return JobHelper.createAndDeleteJobsForTask(schedulerService, changedTask, setProjectForm,
				projectDueDate);
	}

}
