package kr.ac.kaist.se.tardis.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;

@Component
public class SetProjectFormValidator implements Validator {

	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SetProjectForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		SetProjectForm setProjectForm = (SetProjectForm) target;
		if (setProjectForm.getNewMember().length()!=0 && userService.findUserByName(setProjectForm.getNewMember()) == null) {
			errors.rejectValue("newMember", "error.newMember.notExisting", SetProjectForm.NO_EXISITING_USER );
		}
		if (setProjectForm.getProjectName().length()!=0&& setProjectForm.getProjectName().length()< 3 ) {
			errors.rejectValue("projectName", "error.newMember.short", SetProjectForm.SHORT_PROJECT_NAME_ERROR );
		}
	}

}
