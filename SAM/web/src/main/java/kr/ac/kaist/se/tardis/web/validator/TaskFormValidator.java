package kr.ac.kaist.se.tardis.web.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;

@Component
public class TaskFormValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CreateTaskForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CreateTaskForm taskForm = (CreateTaskForm) target;
		if (userService.findUserByName(taskForm.getOwner()) == null) {
			errors.rejectValue("owner", "error.owner.empty", CreateTaskForm.OWNER_NOT_EXIST_ERROR);
		}

		if (!taskForm.getDueDate().trim().isEmpty()) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				format.parse(taskForm.getDueDate());
			} catch (ParseException e) {
				errors.rejectValue("dueDate", "error.dueDate.WrongType", CreateTaskForm.WRONG_DUE_DATE_ERROR);
			}
		}
	}

}
