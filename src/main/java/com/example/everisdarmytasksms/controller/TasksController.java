package com.example.everisdarmytasksms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.everisdarmytasksms.modelo.Task;
import com.example.everisdarmytasksms.service.TaskService;

@CrossOrigin(origins= "http://localhost:4200", maxAge = 3600)
@RestController
public class TasksController {
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="/tasks",method=RequestMethod.GET)
	public List<Task> allUsers() {
		return taskService.findAll();
	}
	@RequestMapping(value="/tasks/count",method=RequestMethod.GET)
	public Long count() {
		return taskService.count();
	}
	
	@RequestMapping(value="/task/{id}",method=RequestMethod.GET)
	public Optional<Task> findId(@PathVariable String id) {
		Long tasksId = Long.parseLong(id);
		return taskService.findById(tasksId);
	}
	
	@RequestMapping(value="/task/{id}",method=RequestMethod.DELETE)
	public Optional<Task> delete(@PathVariable String id) {
		Long tasksId = Long.parseLong(id);
		return taskService.deleteById(tasksId);
	}
	
	@RequestMapping(value="/task",method=RequestMethod.POST)
	public Task insert(@RequestBody Task task) {
		return taskService.saveByEntity(task);
	}
	
	@RequestMapping(value="/task",method=RequestMethod.PUT)
	public Task update(@RequestBody Task task) {
		return taskService.update(task.getId(), task.getFirstName(), task.getTaskDescription(), task.getEstado());
	}
	
	
}