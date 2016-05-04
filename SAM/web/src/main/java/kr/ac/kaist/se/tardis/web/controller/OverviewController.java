package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OverviewController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/overview" })
	public String greeting( Model model) {
		return "overview";
	}
}
