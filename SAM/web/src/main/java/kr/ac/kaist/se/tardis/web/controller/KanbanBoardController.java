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
				BindingResult bindingResult, @RequestParam(name = "projectId", required = true) String projectId, @AuthenticationPrincipal UserDetails user) {
			
			ProjectId id = ProjectIdFactory.valueOf(projectId);
			if (!bindingResult.hasErrors()) {				
				
				Task task = taskService.createTask(String.valueOf(user.getUsername()), projectService.findProjectById(id).get().getId());
				task.setDescription(form.getDescription());
				task.setName(form.getTaskName());
				task.setDueDate(form.getDueDate());
				taskService.saveTask(task);
			}
			fillModel(model, user, id);
			return "KanbanBoard";
		}
		
}
