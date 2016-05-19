package kr.ac.kaist.se.tardis.task.impl.id;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class TaskIdFactoryTest {
	
	@Test
	public void idGeneration(){
		TaskId taskId = TaskIdFactory.generateTaskId();
		TaskId taskId2 = TaskIdFactory.generateTaskId();
		assertThat(taskId, is(not(equalTo(taskId2))));
	}

}
