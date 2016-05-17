package kr.ac.kaist.se.tardis.users.impl.security;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SamUserDetailsServiceTest.TestConfiguration.class)
public class SamUserDetailsServiceTest {
	
	private static final String FOO = "foo";

	private static final UserImpl DEFAULT_USER = new UserImpl(FOO, FOO);
	
	@Autowired
	private SamUserDetailsService detailsService; 
	
	@Test
	public void loadExistingUserByUsername(){
		UserDetails userDetails = detailsService.loadUserByUsername(FOO);
		assertThat(userDetails.getUsername(), is(FOO));
		String password = userDetails.getPassword();
		assertThat(new BCryptPasswordEncoder().matches(FOO, password), is(true));
		assertThat(userDetails.getAuthorities(), is(empty()));
		assertThat(userDetails.isAccountNonExpired(), is(true));
		assertThat(userDetails.isAccountNonLocked(), is(true));
		assertThat(userDetails.isCredentialsNonExpired(), is(true));
		assertThat(userDetails.isEnabled(), is(true));
	}
	
	@Test
	public void loadNonExistingUserByUsername(){
		UserDetails userDetails = detailsService.loadUserByUsername("bar");
		assertThat(userDetails.getUsername(), is("unknown"));
		assertThat(userDetails.getPassword(), is(""));
		assertThat(userDetails.getAuthorities(), is(empty()));
		assertThat(userDetails.isAccountNonExpired(), is(false));
		assertThat(userDetails.isAccountNonLocked(), is(false));
		assertThat(userDetails.isCredentialsNonExpired(), is(false));
		assertThat(userDetails.isEnabled(), is(false));
	}
	
	@Test
	public void userIsProjectOwner(){
		
	}
	
	@Test
	public void userIsNotProjectOwner(){
		
	}
	
	@Test
	public void userIsProjectMember(){
		
	}
	
	@Test
	public void userIsNotProjectMember(){
		
	}
	
	@Test
	public void projectOwnerIsProjectMember(){
		
	}
	
	@Test
	public void userHasTask(){
		
	}
	
	@Test
	public void userDoesNotHaveTask(){
		
	}
	
	@Test
	public void userIsNotAllowedToChangeTask(){
		
	}
	
	@Test
	public void userIsAllowedToChangeTaskBecauseHeOwnsIt(){
		
	}
	
	@Test
	public void userIsAllowedToChangeTaskBecauseHeOwnsTheProject(){
		
	}
	
	@ComponentScan(basePackages="kr.ac.kaist.se.tardis.users.impl.security")
	public static class TestConfiguration{
		
		@Bean
		public UserRepository createMockRepository(){
			UserRepository mock = Mockito.mock(UserRepository.class);
			when(mock.findOne(FOO)).thenReturn(DEFAULT_USER);
			when(mock.findOne("bar")).thenReturn(null);
			return mock;
		}
		
	}
}
