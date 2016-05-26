package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.Pattern;

public class FormWithNotification {

	public static final String WRONG_DUE_DATE_ERROR = "yyyy-MM-dd";

	@Pattern(regexp = "^(\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01]))?$", message = WRONG_DUE_DATE_ERROR)
	private String dueDate;
	private boolean oneDayNotification;
	private boolean threeDayNotification;
	private boolean sevenDayNotificaion;

	public FormWithNotification() {
		super();
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String projectDueDate) {
		this.dueDate = projectDueDate;
	}

	public boolean isOneDayNotification() {
		return oneDayNotification;
	}

	public void setOneDayNotification(boolean oneDayNotification) {
		this.oneDayNotification = oneDayNotification;
	}

	public boolean isThreeDayNotification() {
		return threeDayNotification;
	}

	public void setThreeDayNotification(boolean threeDayNotification) {
		this.threeDayNotification = threeDayNotification;
	}

	public boolean isSevenDayNotificaion() {
		return sevenDayNotificaion;
	}

	public void setSevenDayNotificaion(boolean sevenDayNotificaion) {
		this.sevenDayNotificaion = sevenDayNotificaion;
	}

}