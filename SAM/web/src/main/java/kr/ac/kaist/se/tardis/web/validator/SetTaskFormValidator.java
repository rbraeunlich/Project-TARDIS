package kr.ac.kaist.se.tardis.web.validator;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.SetTaskForm;

@Component
public class SetTaskFormValidator implements Validator {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService projectService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SetTaskForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SetTaskForm setTaskForm = (SetTaskForm) target;
		if (setTaskForm.getOwner() != null) {
			ProjectId id = ProjectIdFactory.valueOf(setTaskForm.getProjectId());

			Set<String> projectMembers = projectService.findProjectById(id).get().getProjectMembers();
			Optional<String> findAny = projectMembers.stream().filter(s -> s.equals(setTaskForm.getOwner())).findAny();

			if (!findAny.isPresent()) {
				errors.rejectValue("owner", "error.Member.notExisting", SetTaskForm.NO_EXISITING_MEMBER);
			}
		}
	}

}
