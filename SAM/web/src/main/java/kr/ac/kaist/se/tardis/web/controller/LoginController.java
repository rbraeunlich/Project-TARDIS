package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
//		model.addAttribute("name", projectService.foo());
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initialWebpage(Model model) {
		return "redirect:index";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.POST)
	public String login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			Model model) {
		return "redirect:overview";
	}

}
