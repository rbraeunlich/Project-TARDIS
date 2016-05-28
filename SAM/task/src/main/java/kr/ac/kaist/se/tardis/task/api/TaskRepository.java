package kr.ac.kaist.se.tardis.task.api;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;

public interface TaskRepository extends JpaRepository<TaskImpl, TaskId> {

}
