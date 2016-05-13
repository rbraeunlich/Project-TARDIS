package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

public class RegistrationControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void successfulRegistration() throws Exception {
		mockMvc
			.perform(post("/registration").param("username", "admin").param("password", "admin"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/index"));
	}
	
	@Test
	public void invalidLogin() throws Exception {
		mockMvc
			.perform(post("/registration").param("username", "").param("password", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"))
			.andExpect(content().string(containsString("Username must contain at least one character")))
			.andExpect(content().string(containsString("Password must contain at least one character")));
	}

}
