CREATE TABLE task (
  id VARCHAR(255),
  name VARCHAR(255),
  description TEXT,
  owner VARCHAR(255),
  dueDate DATE,
  projectId VARCHAR(255),
  taskState VARCHAR(255),
  taskProgress INTEGER,
  key INTEGER UNIQUE,
  PRIMARY KEY(id),
  FOREIGN KEY(owner) REFERENCES userWithoutPassword(username),
  FOREIGN KEY (projectId) REFERENCES project(id) ON DELETE CASCADE
);
