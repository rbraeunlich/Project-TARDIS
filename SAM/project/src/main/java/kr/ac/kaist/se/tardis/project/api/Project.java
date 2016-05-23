package kr.ac.kaist.se.tardis.project.api;

import java.util.Date;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.scheduler.api.JobOwner;

public interface Project extends JobOwner {

	void setName(String name);

	ProjectId getId();

	String getName();
	
	void setDescription(String des);
	
	String getDescription();
	
	Date getDueDate();
	
	void setDueDate(Date d);
	
	void addProjectMember(String member);
	
	void removeProjectMember(String member);
	
	Set<String> getProjectMembers();
	
	String getProjectOwner();

}
