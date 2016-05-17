package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String greeting() {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initialWebpage(Model model) {
		return "redirect:index";
	}

}
