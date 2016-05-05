package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.web.form.LoginForm;

@Controller
public class LoginController {

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String greeting(LoginForm loginForm) {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initialWebpage(Model model) {
		return "redirect:index";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.POST)
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return "index";
		}
		return "redirect:overview";
	}

}
