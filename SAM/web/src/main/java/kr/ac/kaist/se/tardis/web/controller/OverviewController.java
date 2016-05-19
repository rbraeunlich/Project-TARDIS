package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.TaskServiceImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
public class OverviewController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

	@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
	public String overviewpage(Model model, CreateProjectForm form, @AuthenticationPrincipal UserDetails user) {
		fillModel(model, user);
		return "overview";
	}

	private void fillModel(Model model, UserDetails user) {
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("allTaskList", taskService.getAllTasks());
		model.addAttribute("projectList",
				projectService.findProjectsForUser(String.valueOf(user.getUsername())));
		

		
		model.addAttribute("taskList",
				taskService.findTasksForUser(String.valueOf(user.getUsername())));
	}
	
	@RequestMapping(value = { "/overview" }, method = RequestMethod.POST)
	public String projectCreated(Model model, @Valid CreateProjectForm form,
			BindingResult bindingResult, @AuthenticationPrincipal UserDetails user) {
		if (!bindingResult.hasErrors()) {
			Project project = projectService.createProject(String.valueOf(user.getUsername()));
			project.setDescription(form.getDescription());
			project.setName(form.getProjectName());
			projectService.saveProject(project);
		}
		fillModel(model, user);
		return "overview";
	}
}
