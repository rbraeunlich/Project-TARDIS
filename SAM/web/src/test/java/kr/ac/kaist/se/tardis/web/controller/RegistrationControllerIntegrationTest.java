package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import kr.ac.kaist.se.tardis.web.form.RegistrationForm;

public class RegistrationControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void successfulRegistration() throws Exception {
		mockMvc
			.perform(post("/registration")
						.param("username", "admin")
						.param("password", "admin")
						.param("usernameRepeated", "admin")
						.param("passwordRepeated", "admin"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/index"));
	}
	
	@Test
	public void invalidLoginLength() throws Exception {
		mockMvc
		.perform(post("/registration")
				.param("username", "")
				.param("password", "")
				.param("usernameRepeated", "")
				.param("passwordRepeated", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"))
			.andExpect(content().string(containsString(RegistrationForm.USERNAME_LENGTH_ERROR)))
			.andExpect(content().string(containsString(RegistrationForm.PASSWORD_LENGTH_ERROR)));
	}

	@Test
	public void invalidLoginEquality() throws Exception {
		mockMvc
		.perform(post("/registration")
				.param("username", "admin")
				.param("password", "admin")
				.param("usernameRepeated", "admin1")
				.param("passwordRepeated", "admin1"))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"))
			.andExpect(content().string(containsString(RegistrationForm.USERNAME_EQUAL_ERROR)))
			.andExpect(content().string(containsString(RegistrationForm.PASSWORD_EQUAL_ERROR)));
	}
}
