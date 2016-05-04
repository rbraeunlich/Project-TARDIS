package kr.ac.kaist.se.tardis;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.TestRestTemplate.HttpClientOption;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SamApplication.class)
@WebIntegrationTest
public class SamApplicationTests {

	private RestTemplate restTemplate = new TestRestTemplate(new HttpClientOption[0]);

	@Value("${local.server.port}")
	private int port;

	private String testUrl;

	@Before
	public void setUp() {
		testUrl = "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(testUrl, String.class);
		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getBody(), containsString("username"));
		assertThat(responseEntity.getBody(), containsString("password"));
	}

	@Test
	public void succesfulLogin() {
		MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
		request.add("username", "admin");
		request.add("password", "admin");
		URI location = restTemplate.postForLocation(testUrl + "/index", request);
		assertThat(location.toString(), is(testUrl + "/overview"));
	}

}
