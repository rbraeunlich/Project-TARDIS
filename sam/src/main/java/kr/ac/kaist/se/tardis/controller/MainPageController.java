package kr.ac.kaist.se.tardis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String initialWebpage(Model model) {
		return "redirect:index";
	}
}
