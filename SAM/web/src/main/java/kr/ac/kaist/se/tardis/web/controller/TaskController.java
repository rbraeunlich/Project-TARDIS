package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.TaskServiceImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.web.form.CreateTaskForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

/**
 * Created by insanebear on 5/16/16.
 */
@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    private void fillModel(Model model, UserDetails user, ProjectId projectId) {

        model.addAttribute("username", String.valueOf(user.getUsername()));
        model.addAttribute("taskList", taskService.findTaskByProjectId(projectId));
        model.addAttribute("project", projectService.findProjectById(projectId).get());
    }
    
    @RequestMapping(value = {"/taskview"}, method = RequestMethod.GET)
    public String taskInfoView(Model model, @RequestParam(name = "taskId", required = true) String taskId) {
        // show task information on task setting page
        TaskId id = TaskIdFactory.valueOf(taskId);
        Optional<Task> optional = taskService.findTaskById(id);

        if(optional.isPresent())
        {// Task is present in optional
            model.addAttribute("task", optional.get());
        }else{
            //TODO error case

        }

        return "tasksettingview";
    }

    @RequestMapping(value = {"/taskchange"}, method = RequestMethod.GET)
    public String taskChange(Model model, CreateTaskForm form, @RequestParam(name = "taskId", required = true) String taskId,
                             @AuthenticationPrincipal UserDetails user, Task task) {

        TaskId id = TaskIdFactory.valueOf(taskId);
        Optional<Task> optional = taskService.findTaskById(id);

        if(optional.isPresent()){
            Task changedTask = optional.get();
            // TODO update features
            changedTask.setName(task.getName());
            changedTask.setDescription(task.getDescription());
            changedTask.setOwner(task.getOwner());
            taskService.saveTask(task);
            fillModel(model, user, changedTask.getProjectId());
        }else{
            // TODO error case
        }


        return "KanbanBoard";
    }

}
