package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.ArrayList;
import kr.ac.kaist.se.tardis.project.api.*;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.ProjectServiceImpl;

@Controller
public class OverviewController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/overview" })
	public String overviewpage(Model model) {
		List test = new ArrayList();
		
		ProjectService service = new ProjectServiceImpl();
		Project p = service.createProject();
		p.setName("SAM");
		p.setDescription("Project TARDIS");
		test.add(p);
		
		model.addAttribute("username", "Baek");
		model.addAttribute("projectList", test);
		return "overview";
	}
}
