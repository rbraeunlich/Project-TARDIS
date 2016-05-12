package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OverviewController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
	public String overviewpage(Model model, CreateProjectForm form) {
		fillModel(model);
		return "overview";
	}

	private void fillModel(Model model) {
		// FIXME after implementing the accounts, change this
		String user = "Baek";
		model.addAttribute("username", user);
		model.addAttribute("projectList",
				projectService.findProjectsForUser(user));
	}

	@RequestMapping(value = { "/overview" }, method = RequestMethod.POST)
	public String projectCreated(Model model, @Valid CreateProjectForm form,
			BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			Project project = projectService.createProject("Baek");
			project.setDescription(form.getDescription());
			project.setName(form.getProjectName());
			projectService.saveProject(project);
		}
		fillModel(model);
		return "overview";
	}
}
