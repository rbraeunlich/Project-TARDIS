CREATE TABLE projectjobinfo (
	projectid varchar(255),
	jobtype varchar(255),
	githuburl varchar(255),
	triggerid varchar(255),
	disc varchar(255),
	FOREIGN KEY (projectid) REFERENCES project(id) ON DELETE CASCADE,
	PRIMARY KEY (projectid,jobtype)
);

CREATE TABLE taskjobinfo (
	taskid varchar(255),
	jobtype varchar(255),
	triggerid varchar(255),
	disc varchar(255),
	FOREIGN KEY (taskid) REFERENCES task(id) ON DELETE CASCADE,
	PRIMARY KEY (taskid,jobtype)
);