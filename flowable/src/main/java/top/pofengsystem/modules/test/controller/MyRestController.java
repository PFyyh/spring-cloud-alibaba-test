package top.pofengsystem.modules.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.pofengsystem.modules.test.entity.TaskRepresentation;
import top.pofengsystem.modules.test.service.MyService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class MyRestController {

    @Autowired
    private MyService myService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public void startProcessInstance() {
        myService.startProcess();
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        log.info("TASK num:"+tasks.size());
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }


}