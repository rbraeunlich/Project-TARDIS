package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
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
public class LoginControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void verifyRedirect() throws Exception {
		mockMvc
			.perform(get("/"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/index"));
	}
	
	@Test
	public void indexPageDisplayed() throws Exception {
		mockMvc
			.perform(get("/index"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("username")))
			.andExpect(content().string(containsString("password")));
	}
	
	@Test
	public void successfulLogin() throws Exception {
		mockMvc
			.perform(post("/index").param("username", "admin").param("password", "admin"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/overview"));
	}

}
