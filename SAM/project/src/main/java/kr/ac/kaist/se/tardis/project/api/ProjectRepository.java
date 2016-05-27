package kr.ac.kaist.se.tardis.project.api;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface ProjectRepository extends JpaRepository<ProjectImpl, ProjectId> {

}
