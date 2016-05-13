package kr.ac.kaist.se.tardis.web.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.ac.kaist.se.tardis.SamApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SamApplication.class)
@WebIntegrationTest
public class AbstractControllerIntegrationTest {

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
	@Before
	public void setUpAbstractControllerIntegrationTest() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
}
