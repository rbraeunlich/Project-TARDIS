package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.RegistrationForm;
import kr.ac.kaist.se.tardis.web.validator.UsernameAndPasswordRepititionValidator;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UsernameAndPasswordRepititionValidator validator;
	
	@RequestMapping(value = { "/registration" })
	public String showWebsite(RegistrationForm loginForm) {
		return "registration";
	}

	@RequestMapping(value = { "/registration" }, method = RequestMethod.POST)
	public String registration(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
		validator.validate(registrationForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userService.createUser(registrationForm.getUsername(), registrationForm.getPassword());
		return "redirect:index";
	}

}
