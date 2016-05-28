CREATE TABLE tasknote (
  id VARCHAR(255),
  taskid VARCHAR(255),
  author VARCHAR(255),
  writeDate TIMESTAMP,
  FOREIGN KEY (taskid) REFERENCES task(id) ON DELETE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE comment (
  id VARCHAR(255),
  tasknoteid VARCHAR(255),
  comment TEXT,
  FOREIGN KEY (tasknoteid) references tasknote(id)
);

CREATE table contribution (
  id VARCHAR(255),
  tasknoteid VARCHAR(255),
  contribution INTEGER,
  progress INTEGER,
  FOREIGN KEY (tasknoteid) references tasknote(id)
);
