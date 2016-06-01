package kr.ac.kaist.se.tardis.users.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@Profile("default")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SamUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/registration", "/", "/image/**", "/css/**").permitAll()
				.regexMatchers("/index", "/index\\?.*").permitAll()
				.regexMatchers("/kanbanboard", "/kanbanboard\\?.*").access("@samUserDetailsService.isUserPartOfProject(authentication,request)")
				.regexMatchers("/taskchange", "/taskchange\\?.*").access("@samUserDetailsService.isUserAllowedToChangeTask(authentication,request)")
				.regexMatchers("/projectchange", "/projectchange\\?.*").access("@samUserDetailsService.isUserProjectOwner(authentication,request)")
				.regexMatchers("/projectsettingview", "/projectsettingview\\?.*").access("@samUserDetailsService.isUserProjectOwner(authentication,request)")
				.regexMatchers("/updatestaskstatus", "/updatestaskstatus\\?.*").access("@samUserDetailsService.isUserAllowedToChangeTask(authentication,request)")
				.regexMatchers("/tasksettingview", "/tasksettingview\\?.*").access("@samUserDetailsService.isUserAllowedToChangeTask(authentication,request)")
				.regexMatchers("/taskDetail", "/taskDetail\\?.*").access("@samUserDetailsService.isUserAllowedToSeeTask(authentication,request)")
				.regexMatchers("/addtask", "/addtask\\?.*").access("@samUserDetailsService.isUserAllowedToSeeTask(authentication,request)")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/index")
				.defaultSuccessUrl("/overview")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/index?logout")
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
