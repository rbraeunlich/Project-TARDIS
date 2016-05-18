package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

@Controller
public class KanbanBoardController {

		@Autowired
		private ProjectService projectService;
		
		@RequestMapping(value = { "/kanbanboard" }, method = RequestMethod.GET)
		public String projectManagementpage(Model model, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("username", String.valueOf(user.getUsername()));
			return "KanbanBoard";
		}
	
}
