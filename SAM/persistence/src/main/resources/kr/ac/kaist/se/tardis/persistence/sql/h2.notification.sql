CREATE SEQUENCE NOTIFICATION_ID_SEQ;

CREATE TABLE notification (
	id integer,
	username varchar(255),
	notificationText text,
	notificationDate date,
	primary key(id),
	foreign key (username) references userWithoutPassword(username)
);