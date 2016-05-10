package kr.ac.kaist.se.tardis.project.api;

import java.util.Date;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface Project {

	void setName(String name);

	ProjectId getId();

	String getName();
	
	Date getDueDate();
	
	void setDueDate(Date d);
	
	void addProjectMember(String member);
	
	void removeProjectMember(String member);
	
	Set<String> getProjectMembers();
	
	String getProjectOwner();

}
