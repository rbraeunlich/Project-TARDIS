package kr.ac.kaist.se.tardis.web.controller;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.TaskServiceImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

/**
 * Created by insanebear on 5/16/16.
 */
@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = {"/taskview"}, method = RequestMethod.GET)
    public String taskInfoView(Model model, @RequestParam(name = "taskId", required = true) String taskId) {
        // show task information on task setting page
        TaskId id = TaskIdFactory.valueOf(taskId);
        Optional<Task> optional = taskService.findTaskById(id);
        //** Test lines
        Task fakeTask = taskService.createTask("Yurim Jeong", ProjectIdFactory.generateProjectId());
        fakeTask.setName("TestTask");
        fakeTask.setDescription("This is a testProject");
        fakeTask.setDueDate(new Date());
        model.addAttribute("task", fakeTask);
        //**
        if(optional.isPresent())
        {// Task is present in optional
            model.addAttribute("task", optional.get());
        }else{
            //TODO error case

        }

        return "tasksettingview";}

    @RequestMapping(value = {"/taskchange"}, method = RequestMethod.POST)
    public String taskChange(Model model, Task task) {
        //Test line
        System.out.println("Task name: " + task.getName());
        System.out.println("Task owner: " + task.getTaskOwner());
        System.out.println("Task description: " + task.getDescription());
        //
        //taskService.saveTask(task);

        return "KanbanBoard";
    }

}
