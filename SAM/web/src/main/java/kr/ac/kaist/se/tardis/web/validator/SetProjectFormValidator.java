package kr.ac.kaist.se.tardis.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;

@Component
public class SetProjectFormValidator implements Validator {

	@Autowired
	UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SetProjectForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SetProjectForm setProjectForm = (SetProjectForm) target;
		if (setProjectForm.getNewMember() != null && setProjectForm.getNewMember().length()!=0 && userService.findUserByName(setProjectForm.getNewMember()) == null) {
			errors.rejectValue("newMember", "error.newMember.notExisting", SetProjectForm.NO_EXISITING_USER );
		}
		if(setProjectForm.getProjectName() == null){
			errors.rejectValue("projectName", "error.newMember.short", SetProjectForm.SHORT_PROJECT_NAME_ERROR );
		}
		else if (setProjectForm.getProjectName().length()< 3 ) {
			errors.rejectValue("projectName", "error.newMember.short", SetProjectForm.SHORT_PROJECT_NAME_ERROR );
		}
		if(setProjectForm.getDueDate() == null || setProjectForm.getDueDate().isEmpty()){
			if(setProjectForm.isOneDayNotification() || setProjectForm.isThreeDayNotification() || setProjectForm.isSevenDayNotification()){
				errors.rejectValue("dueDate", "error.dueDate", SetProjectForm.NOTIFICATION_WITHOUT_DUE_DATE);
			}
		}

	}

}
