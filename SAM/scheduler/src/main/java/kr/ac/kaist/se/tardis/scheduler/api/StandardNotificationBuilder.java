package kr.ac.kaist.se.tardis.scheduler.api;

import java.util.Date;

public interface StandardNotificationBuilder {

	StandardNotificationBuilder forTask(String id);
	
	StandardNotificationBuilder forProject(String id);
	
	StandardNotificationBuilder threeDays(Date endDate);

	StandardNotificationBuilder sevenDays(Date endDate);
	
	StandardNotificationBuilder fourteenDays(Date endDate);
	
	JobInfo submit();
}
