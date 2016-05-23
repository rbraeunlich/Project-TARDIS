package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.TaskServiceImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;
import kr.ac.kaist.se.tardis.web.validator.SetProjectFormValidator;

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
import java.util.Date;
import java.util.Optional;

@Controller
public class ProjectController {
	@Autowired
	private SetProjectFormValidator validator;
	@Autowired
	private ProjectService projectService;

	@Autowired
	private TaskService taskService;

	private void fillModel(Model model, UserDetails user, ProjectId projectId) {

		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("taskList", taskService.findTaskByProjectId(projectId));
		model.addAttribute("project", projectService.findProjectById(projectId).get());
	}

	@RequestMapping(value = { "/projectsettingview" }, method = RequestMethod.POST)
	public String projectInfoView(Model model, @RequestParam(name = "projectId", required = true) String projectId,
			@AuthenticationPrincipal UserDetails user, @Valid SetProjectForm setProjectForm, BindingResult bindingResult) {
		// show project information on project setting page
		
		if(setProjectForm.getProjectName()!=null || setProjectForm.getNewMember()!=null)
			validator.validate(setProjectForm, bindingResult);
		
		ProjectId id = ProjectIdFactory.valueOf(projectId);
		fillModel(model, user, id);

		Optional<Project> optional = projectService.findProjectById(id);

		if (optional.isPresent()) {
			Project thisProject = optional.get();
			// //test line for memebers
			// thisProject.addProjectMember("member1");
			// thisProject.addProjectMember("member2");
			// //
			model.addAttribute("project", thisProject);
		} else {
			// TODO error case

		}
		return "projectsettingview";
	}
	


	@RequestMapping(value = { "/projectchange" }, method = RequestMethod.POST)
	public String projectChange(Model model, CreateTaskForm form,
			@RequestParam(name = "projectId", required = true) String projectId,
			@AuthenticationPrincipal UserDetails user, @Valid SetProjectForm setProjectForm, BindingResult bindingResult) {

		ProjectId id = ProjectIdFactory.valueOf(projectId);

		validator.validate(setProjectForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
			
			return "forward:projectsettingview";
		}

		Optional<Project> optional = projectService.findProjectById(id);

		if (optional.isPresent()) {
			// TODO update features
			Project changedProject = optional.get();
			if (setProjectForm.getProjectName().length() != 0)
				changedProject.setName(setProjectForm.getProjectName());
			if (setProjectForm.getDescription().length() != 0)
				changedProject.setDescription(setProjectForm.getDescription());
			if (setProjectForm.getNewMember().length() != 0)
				changedProject.addProjectMember(setProjectForm.getNewMember());

			projectService.saveProject(changedProject);

			fillModel(model, user, id);
		} else {
			// TODO error case
		}
		return "KanbanBoard";
	}

}
