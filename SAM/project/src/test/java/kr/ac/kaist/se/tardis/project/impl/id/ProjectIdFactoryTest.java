package kr.ac.kaist.se.tardis.project.impl.id;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class ProjectIdFactoryTest {
	
	@Test
	public void idGeneration(){
		ProjectId projectId = ProjectIdFactory.generateProjectId();
		ProjectId projectId2 = ProjectIdFactory.generateProjectId();
		assertThat(projectId, is(not(equalTo(projectId2))));
	}

}
