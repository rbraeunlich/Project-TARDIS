package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.*;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KanbanBoardController {

		@Autowired
		private ProjectService projectService;
		
		@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.GET)
		public String projectManagementpage(Model model, @RequestParam(name = "projectId", required = true) String projectId, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("username", user.getUsername());
			
			ProjectId id = ProjectIdFactory.valueOf(projectId);
			model.addAttribute("project", projectService.findProjectById(id).get());
			return "KanbanBoard";
		}
	
}
