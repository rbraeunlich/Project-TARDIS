package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Profile("dev")
	@RequestMapping(value = { "/index" }, method = RequestMethod.POST)
	public String login() {
		Authentication auth = new TestingAuthenticationToken("Test-User", "");
		SecurityContextHolder.getContext().setAuthentication(auth);
		return "redirect:overview";
	}

}
