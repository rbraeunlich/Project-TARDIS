package kr.ac.kaist.se.tardis.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.web.form.SetUserForm;

@Component
public class PasswordRepititionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SetUserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SetUserForm form = (SetUserForm) target;
		if (!form.getPassword().equals(form.getPasswordRepeated())) {
			errors.rejectValue("passwordRepeated", "error.password.repeated", SetUserForm.PASSWORD_EQUAL_ERROR);
		}
	}

}
