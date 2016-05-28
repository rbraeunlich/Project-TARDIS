CREATE TABLE jobinfo (
	taskid varchar(255),
	projectid varchar(255),
	jobtype varchar(255),
	githuburl varchar(255),
	triggerid varchar(255),
	FOREIGN KEY (projectid) REFERENCES project(id)
);