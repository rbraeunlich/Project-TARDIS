package kr.ac.kaist.se.tardis.project.impl;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class ProjectServiceImplTest {
	
	@Autowired
	ProjectService projectService;

	@Test
	public void createProject(){
		
	}
}
