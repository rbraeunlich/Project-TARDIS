package kr.ac.kaist.se.tardis.users.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SamUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/registration", "/", "/image/**", "/css/**").permitAll()
				.antMatchers("/KanbanBoard").access("@samUserDetailsService.isUserProjectMember(authentication,request)")
				.antMatchers("/taskview").access("@samUserDetailsService.isUserProjectMember(authentication,request)")
				.antMatchers("/taskchange").access("@samUserDetailsService.hasUserTask(authentication,request)")
				.antMatchers("/projectchange").access("@samUserDetailsService.isUserProjectOwner(authentication,request)")
				//TODO check if all those URLs match
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/index")
				.defaultSuccessUrl("/overview")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/index")
				.invalidateHttpSession(true)
			.and()
				.authenticationProvider(createAuthentificationProvier())
				.userDetailsService(userDetailsService);
	}

	private AuthenticationProvider createAuthentificationProvier() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
