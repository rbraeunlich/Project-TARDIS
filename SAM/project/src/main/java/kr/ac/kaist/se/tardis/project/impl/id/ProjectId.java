package kr.ac.kaist.se.tardis.project.impl.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProjectId implements Comparable<ProjectId>, Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	ProjectId(){}
	
	ProjectId(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}
	
	@Override
	public int compareTo(ProjectId o) {
		return this.id.compareTo(o.getId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectId other = (ProjectId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
