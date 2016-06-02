package kr.ac.kaist.se.tardis.web.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;

@Controller
public class DeleteNotificationController {

	@Autowired
	private NotificationService service;
	
	@RequestMapping(path = "deletenotification", method = RequestMethod.POST)
	public String deleteNotification(@RequestParam(name = "notificationId", required = true) String notificationId,
			@RequestParam(name = "currentUrl", required = true) String currentUrl,
			RedirectAttributes redirectAttributes) {
		service.deleteNotification(notificationId);
		URL url = null;
		try {
			url = new URL(currentUrl);
			List<NameValuePair> parse = URLEncodedUtils.parse(url.toURI(), "UTF-8");
			for (NameValuePair nameValuePair : parse) {
				redirectAttributes.addAttribute(nameValuePair.getName(), nameValuePair.getValue());
			}
		} catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return "redirect:" + url.getPath();
	}
}
