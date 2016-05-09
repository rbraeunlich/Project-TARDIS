package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.web.form.LoginForm;

@Controller
public class RegistrationController {

	@RequestMapping(value = { "/registration" })
	public String showWebsite(LoginForm loginForm) {
		return "registration";
	}

	@RequestMapping(value = { "/registration" }, method = RequestMethod.POST)
	public String registration(@Valid LoginForm loginForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		return "redirect:index";
	}

}
