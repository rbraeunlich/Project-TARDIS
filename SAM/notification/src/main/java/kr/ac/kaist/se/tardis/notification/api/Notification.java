package kr.ac.kaist.se.tardis.notification.api;

import java.util.Date;

public interface Notification {

	Date getNotificationDate();

	String getText();

	String getUsername();

	Long getId();

}
