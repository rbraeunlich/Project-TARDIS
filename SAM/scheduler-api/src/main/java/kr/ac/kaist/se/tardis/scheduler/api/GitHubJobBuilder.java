package kr.ac.kaist.se.tardis.scheduler.api;

public interface GitHubJobBuilder {
	
	GitHubJobBuilder forRepository(String url);
	
	GitHubJobBuilder forProject(String id);
	
	JobInfo submit();
	
}
