CREATE TABLE project (
	id varchar(255),
	name varchar(255),
	description text,
	owner varchar(255),
	dueDate date,
	gitHubUrl VARCHAR(255),
	PRIMARY KEY(id),
	FOREIGN KEY(owner) REFERENCES userWithoutPassword(username)
);

CREATE TABLE projectmember (
	projectid varchar(255),
	members varchar(255),
	FOREIGN KEY(projectid) REFERENCES project(id),
	FOREIGN KEY(members) REFERENCES userWithoutPassword(username)
);