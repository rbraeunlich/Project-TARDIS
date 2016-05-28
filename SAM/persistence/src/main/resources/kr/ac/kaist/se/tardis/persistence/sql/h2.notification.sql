CREATE SEQUENCE NOTIFICATION_ID_SEQ;

CREATE TABLE notification (
	id integer,
	username varchar(255),
	notificationText text,
	notificationDate date,
	primary key(id),
	FOREIGN KEY (username) REFERENCES userWithoutPassword(username)
);