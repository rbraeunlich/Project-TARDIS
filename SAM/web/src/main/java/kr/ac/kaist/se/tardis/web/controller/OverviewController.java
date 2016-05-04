package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

@Controller
public class OverviewController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/overview" })
	public String greeting( Model model) {
		return "overview";
	}
}
