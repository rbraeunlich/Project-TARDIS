package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.notification.api.NotificationService;

import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Set;

@Controller
public class OverviewController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
	public String overviewpage(Model model, CreateProjectForm form, @AuthenticationPrincipal UserDetails user) {
		fillModel(model, user);
		return "overview";
	}

	private void fillModel(Model model, UserDetails user) {
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("allTaskList", taskService.getAllTasks());
		model.addAttribute("projectList", projectService.findProjectsForUser(String.valueOf(user.getUsername())));

		Set<Task> tasks = taskService.findTasksForUser(String.valueOf(user.getUsername()));
		for (Task t : tasks) {
			t.setProjectName(projectService.findProjectById(t.getProjectId()).get().getName());
		}

		model.addAttribute("taskList", tasks);
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
	}

	@RequestMapping(value = { "/overview" }, method = RequestMethod.POST)
	public String projectCreated(Model model, @Valid CreateProjectForm form, BindingResult bindingResult,
			@AuthenticationPrincipal UserDetails user) {
		if (!bindingResult.hasErrors()) {
			Project project = projectService.createProject(String.valueOf(user.getUsername()));
			project.setDescription(form.getDescription());
			project.setName(form.getProjectName());
			projectService.saveProject(project);
			fillModel(model, user);
			return "redirect:overview";
		} else {
			fillModel(model, user);
			return "overview";
		}
	}
}
