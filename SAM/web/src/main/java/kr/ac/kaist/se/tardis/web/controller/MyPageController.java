package kr.ac.kaist.se.tardis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;

@Controller
public class MyPageController {
	
	@Autowired
	private NotificationService notificationService;

	private void fillModel(Model model, UserDetails user) {
		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
	}
	
	@RequestMapping(value = { "/mypage" }, method = RequestMethod.POST)
	public String mypage(Model model, @AuthenticationPrincipal UserDetails user) {
		fillModel(model, user);
		return "mypage";
	}	
}
