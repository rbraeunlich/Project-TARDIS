package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.*;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

import java.util.Optional;

@Controller
public class KanbanBoardController {

		@Autowired
		private ProjectService projectService;
		
		@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.GET)
		public String projectManagementpage(Model model, @RequestParam(name = "projectId", required = true) String projectId, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("username", String.valueOf(user.getUsername()));
			
			ProjectId id = ProjectIdFactory.valueOf(projectId);
			model.addAttribute("project", projectService.findProjectById(id).get());

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
			//test line
			thisProject.addProjectMember("member1");
			thisProject.addProjectMember("member2");
			//
			model.addAttribute("project", thisProject);
		}else{
			//TODO error case

		}
		return "projectsettingview";
	}

	@RequestMapping(value={"/projectchange"}, method = RequestMethod.POST)
	public String projectChange(Model model, Project project){
		// Test lines

		return "overview";
	}

}

