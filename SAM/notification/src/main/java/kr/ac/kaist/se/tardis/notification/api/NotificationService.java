package kr.ac.kaist.se.tardis.notification.api;

import java.util.Date;
import java.util.Set;

public interface NotificationService {

	void createNotification(String username, String text, Date notificationDate);

	Set<Notification> getNotificationsForUser(String username);
}
