package kr.ac.kaist.se.tardis.web.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;
import kr.ac.kaist.se.tardis.web.form.SetTaskForm;

@Component
public class SetTaskFormValidator implements Validator {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	@Autowired
	ProjectService proejctService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return SetTaskForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		SetTaskForm setTaskForm = (SetTaskForm) target;

		if (setTaskForm.getTaskName().length() != 0 && setTaskForm.getTaskName().length() > 3) {
			Set<Task> tasks = taskService.findTaskByName(setTaskForm.getTaskName());

			for (Task t : tasks) {
				if (t.getProjectId().getId().equals(setTaskForm.getProjectId())) {
					errors.rejectValue("taskName", "error.taskName.dup", SetTaskForm.DUP_TASK_NAME_ERROR);
					break;
				}
			}
		}else{
			if(setTaskForm.getTaskName().length() != 0)
				errors.rejectValue("taskName", "error.taskName.short", SetTaskForm.SHORT_TASK_NAME_ERROR);
		}

		if (setTaskForm.getOwner().length() != 0) {
			System.out.println("@@@@@@"+setTaskForm.getProjectId()+"@@@@@@");
			ProjectId id = ProjectIdFactory.valueOf(setTaskForm.getProjectId());

			Set<String> projectMembers = proejctService.findProjectById(id).get().getProjectMembers();
			boolean find = false;

			for (String m : projectMembers) {
				if (m.equals(setTaskForm.getOwner())) {
					find = true;
					break;
				}
			}

			if (!find) {
				errors.rejectValue("owner", "error.Member.notExisting", SetTaskForm.NO_EXISITING_MEMBER);
			}
		}
	}

}
