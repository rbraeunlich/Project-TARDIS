package kr.ac.kaist.se.tardis.web.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;

@Component
public class TaskFormValidator implements Validator {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CreateTaskForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CreateTaskForm taskForm = (CreateTaskForm) target;
		Set<Task> tasks =taskService.findTaskByName(taskForm.getTaskName());
		
		for (Task t : tasks) {
			if(t.getProjectId().getId().equals(taskForm.getProjectId())){
				errors.rejectValue("taskName", "error.taskName.dup", CreateTaskForm.DUP_TASK_NAME_ERROR);
				break;
			}
		}
		if (userService.findUserByName(taskForm.getOwner()) == null) {
			errors.rejectValue("owner", "error.owner.empty", CreateTaskForm.OWNER_NOT_EXIST_ERROR);
		}
	}

}
