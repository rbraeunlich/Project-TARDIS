package kr.ac.kaist.se.tardis.task.api;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;

public interface TaskRepository extends JpaRepository<TaskImpl, TaskId> {

	Set<Task> findTaskByProject(ProjectId id);

	Set<Task> findTasksByName(String name);

	Set<Task> findTasksByOwner(String userId);

}
