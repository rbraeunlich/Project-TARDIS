package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

	@RequestMapping(value = { "/registration" })
	public String greeting(Model model) {
		return "registration";
	}
}
