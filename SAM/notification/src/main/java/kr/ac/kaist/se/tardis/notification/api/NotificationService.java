package kr.ac.kaist.se.tardis.notification.api;

import java.util.Date;

public interface NotificationService {

	void createNotification(String username, String text, Date notificationDate);

}
