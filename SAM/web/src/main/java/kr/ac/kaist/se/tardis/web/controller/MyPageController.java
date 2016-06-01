package kr.ac.kaist.se.tardis.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.SetUserForm;
import kr.ac.kaist.se.tardis.web.validator.PasswordRepititionValidator;

@Controller
public class MyPageController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private PasswordRepititionValidator validator;

	private void fillModel(Model model, UserDetails user, SetUserForm userForm) {
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
		model.addAttribute("userForm", userForm);
	}
	
	@RequestMapping(value = { "/mypage" }, method = RequestMethod.GET)
	public String mypage(SetUserForm userForm, Model model, @AuthenticationPrincipal UserDetails user) {
		fillModel(model, user, userForm);
		return "mypage";
	}	
	
	@RequestMapping(value = { "/mypage" }, method = RequestMethod.POST)
	public String changePassword(Model model, @Valid SetUserForm userForm, BindingResult bindingResult, 
				@AuthenticationPrincipal UserDetails user) {
		validator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "mypage";
		}		
		userService.changePassword(userService.findUserByName(user.getUsername()), userForm.getPassword());
		return "forward:overview";
	}
}
