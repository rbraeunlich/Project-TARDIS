package kr.ac.kaist.se.tardis.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.scheduler.api.SchedulerService;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import kr.ac.kaist.se.tardis.web.form.FormWithNotification;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;
import kr.ac.kaist.se.tardis.web.validator.SetProjectFormValidator;

@Controller
public class ProjectController {
	@Autowired
	private SetProjectFormValidator validator;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private SchedulerService schedulerService;

	private void fillModel(Model model, UserDetails user, ProjectId projectId) {

		model.addAttribute("username", String.valueOf(user.getUsername()));
		model.addAttribute("taskList", taskService.findTaskByProjectId(projectId));
		model.addAttribute("project", projectService.findProjectById(projectId).get());
		model.addAttribute("notificationList", notificationService.getNotificationsForUser(user.getUsername()));
	}

	@RequestMapping(value = { "/projectsettingview" }, method = RequestMethod.POST)
	public String projectInfoView(Model model, @RequestParam(name = "projectId", required = true) String projectId,
			@AuthenticationPrincipal UserDetails user, @Valid SetProjectForm setProjectForm,
			BindingResult bindingResult) {
		// show project information on project setting page

		if (setProjectForm.getProjectName() != null || setProjectForm.getNewMember() != null)
			validator.validate(setProjectForm, bindingResult);

		ProjectId id = ProjectIdFactory.valueOf(projectId);
		fillModel(model, user, id);

		Optional<Project> optional = projectService.findProjectById(id);

		if (optional.isPresent()) {
			Project thisProject = optional.get();
			model.addAttribute("project", thisProject);
		} else {
			// TODO error case

		}
		return "projectsettingview";
	}

	@RequestMapping(value = { "/projectchange" }, method = RequestMethod.POST)
	public String projectChange(Model model, CreateTaskForm form,
			@RequestParam(name = "projectId", required = true) String projectId,
			@AuthenticationPrincipal UserDetails user, @Valid SetProjectForm setProjectForm,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		ProjectId id = ProjectIdFactory.valueOf(projectId);

		validator.validate(setProjectForm, bindingResult);

		if (bindingResult.hasErrors()) {

			return "forward:projectsettingview";
		}

		Optional<Project> optional = projectService.findProjectById(id);

		if (optional.isPresent()) {
			// TODO update features
			Project changedProject = optional.get();
			changedProject.setName(setProjectForm.getProjectName());
			changedProject.setDescription(setProjectForm.getDescription());
			String newMember = setProjectForm.getNewMember();
			if (newMember != null && !newMember.trim().isEmpty()) {
				changedProject.addProjectMember(newMember);
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date projectDueDate = null;
			try {
				String dueDate = setProjectForm.getDueDate();
				if (dueDate != null && !dueDate.trim().isEmpty()) {
					projectDueDate = dateFormat.parse(dueDate);
					changedProject.setDueDate(projectDueDate);
					changedProject = createAndDeleteJobsForProject(changedProject, setProjectForm, projectDueDate);
				}
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			projectService.saveProject(changedProject);

			fillModel(model, user, id);
		} else {
			// TODO error case
		}
		redirectAttributes.addAttribute("projectId", form.getProjectId());
		return "redirect:kanbanboard";
	}

	/**
	 * Depending on how the checkboxes were changed, the notifications for a
	 * project must either be added or removed.
	 * 
	 * @param changedProject
	 * @param setProjectForm
	 * @param projectDueDate
	 * @return
	 */
	private Project createAndDeleteJobsForProject(Project changedProject, FormWithNotification setProjectForm,
			Date projectDueDate) {
		return JobHelper.createAndDeleteJobsForProject(schedulerService, changedProject, setProjectForm,
				projectDueDate);
	}

}
