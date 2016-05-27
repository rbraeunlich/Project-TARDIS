package kr.ac.kaist.se.tardis.scheduler.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobInfoRepository extends JpaRepository<JobInfo, String>{

}
