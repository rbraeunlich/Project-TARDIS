package kr.ac.kaist.se.tardis.notification.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.notification.api.Notification;
import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.notification.impl.persistence.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository repo;

	@Override
	public void createNotification(String username, String text,
			Date notificationDate) {
		NotificationImpl notification = new NotificationImpl();
		notification.setUsername(username);
		notification.setText(text);
		notification.setNotificationDate(notificationDate);
		repo.save(notification);
	}

	@Override
	public Set<Notification> getNotificationsForUser(String username) {
		return repo.findNotificationsByUsername(username);
	}

}
