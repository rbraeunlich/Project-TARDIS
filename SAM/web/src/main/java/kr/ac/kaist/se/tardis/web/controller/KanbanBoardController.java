package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.*;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.task.api.*;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

import java.util.Optional;

@Controller
public class KanbanBoardController {

		@Autowired
		private ProjectService projectService;
		
		@Autowired
		private TaskService taskService;
		
		private void fillModel(Model model, UserDetails user, ProjectId projectId) {
			
			model.addAttribute("username", String.valueOf(user.getUsername()));
			model.addAttribute("taskList",
					taskService.findTaskByProjectId(projectId));
			model.addAttribute("project", projectService.findProjectById(projectId).get());
		}

		@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.GET)
		public String projectManagementpage(Model model, CreateTaskForm form, @RequestParam(name = "projectId", required = true) String projectId, @AuthenticationPrincipal UserDetails user) {
			ProjectId id = ProjectIdFactory.valueOf(projectId);
			
			fillModel(model, user, id);
			return "KanbanBoard";
		}
	
		@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.POST)
		public String taskCreated(Model model, @Valid CreateTaskForm form,
				BindingResult bindingResult, @AuthenticationPrincipal UserDetails user) {
			
			ProjectId id = ProjectIdFactory.valueOf(form.getProjectId());

			if (!bindingResult.hasErrors()) {					
				
				Task task = taskService.createTask(String.valueOf(user.getUsername()), id);
				task.setDescription(form.getTaskDescription());
				task.setName(form.getTaskName());
				task.setOwner(form.getOwner());
				task.setTaskProgress(0);
				taskService.saveTask(task);
			}
			
			fillModel(model, user, id);
			return "KanbanBoard";
		}

	@RequestMapping(value={"/projectview"}, method = RequestMethod.GET)
	public String projectInfoView(Model model, @RequestParam(name = "projectId", required = true) String projectId){
		// show project information on project setting page
		ProjectId id = ProjectIdFactory.valueOf(projectId);
		Optional<Project> optional = projectService.findProjectById(id);

		if(optional.isPresent())
		{// Project is present in optional
			Project thisProject = optional.get();
			//test line for memebers
			thisProject.addProjectMember("member1");
			thisProject.addProjectMember("member2");
			System.out.println(thisProject.getId());
			//
			model.addAttribute("project", thisProject);
		}else{
			//TODO error case

		}
		return "projectsettingview";
	}

	@RequestMapping(value={"/projectchange"}, method = RequestMethod.GET)
	public String projectChange(Model model, CreateTaskForm form, @RequestParam(name = "projectId", required = true)String projectId, @AuthenticationPrincipal UserDetails user, Project project){
		ProjectId id = ProjectIdFactory.valueOf(projectId);
		Optional<Project> optional = projectService.findProjectById(id);

		if(optional.isPresent()){
			//TODO update features
			Project changedProject = optional.get();
			changedProject.setName(project.getName());
			changedProject.setDescription(project.getDescription());
			projectService.saveProject(changedProject);
			fillModel(model, user, id);
		}else{
			//TODO error case
		}
		return "KanbanBoard";
	}
}

