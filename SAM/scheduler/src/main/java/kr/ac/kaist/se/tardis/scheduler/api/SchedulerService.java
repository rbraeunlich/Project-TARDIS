package kr.ac.kaist.se.tardis.scheduler.api;

public interface SchedulerService {

	GitHubJobBuilder createGitHubJobBuilder();
	
	StandardNotificationBuilder createNotificationBuilder();
	
}
