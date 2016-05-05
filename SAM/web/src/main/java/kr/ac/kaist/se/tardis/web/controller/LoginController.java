package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String greeting(Model model) {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initialWebpage(Model model) {
		return "redirect:index";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.POST)
	public String login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password, Model model) {
		return "redirect:overview";
	}

}
