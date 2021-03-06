package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class LoginControllerIntegrationTest extends AbstractControllerIntegrationTest{

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
	
}
