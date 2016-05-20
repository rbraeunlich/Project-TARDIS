package kr.ac.kaist.se.tardis.task.impl.id;

import java.io.Serializable;

public class TaskId implements Comparable<TaskId>, Serializable{

	private static final long serialVersionUID = 1L;
	private final String id;
	
	TaskId(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public int compareTo(TaskId o) {
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
		TaskId other = (TaskId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
