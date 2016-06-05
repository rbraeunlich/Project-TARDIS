package kr.ac.kaist.se.tardis.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.RegistrationForm;

@Component
public class UsernameAndPasswordRepititionValidator implements Validator {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationForm form = (RegistrationForm) target;
		if (!form.getUsername().equals(form.getUsernameRepeated())) {
			errors.rejectValue("usernameRepeated", "error.username.repeated", RegistrationForm.USERNAME_EQUAL_ERROR);
		}
		if (!form.getPassword().equals(form.getPasswordRepeated())) {
			errors.rejectValue("passwordRepeated", "error.password.repeated", RegistrationForm.PASSWORD_EQUAL_ERROR);
		}
		if(userService.findUserByName(form.getUsername())!=null){
			errors.rejectValue("username", "error.username.dup", RegistrationForm.USERNAME_DUP);
		}
	}

}
