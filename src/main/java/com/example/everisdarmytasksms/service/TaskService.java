package com.example.everisdarmytasksms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.everisdarmytasksms.modelo.Task;
import com.example.everisdarmytasksms.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {

        var it = taskRepository.findAll();

        var users = new ArrayList<Task>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Long count() {

        return taskRepository.count();
    }

    public Optional<Task> deleteById(Long id) {
    	Optional<Task> deleted = taskRepository.findById(id);
    	taskRepository.deleteById(id);
    	return deleted;
    }
    public Optional<Task> findById(Long userId) {

    	return taskRepository.findById(userId);
    	
    }
    public Task saveByEntity(Task entity) {
    	if(entity != null) {
    		return taskRepository.save(entity);
    	}else {
    		return null;
    	}
    	
    }
    public Task update(Long userId,String firstName,String taskDescription,String estado) {
    	Task antigua = taskRepository.findById(userId).get();
    	antigua.setFirstName(firstName);
    	antigua.setTaskDescription(taskDescription);
    	antigua.setEstado(estado);
    	taskRepository.save(antigua);
    	return antigua;
    }
    public List<Task> findAllWithState(String estado) {
    	List<Task> listaCompleta = this.findAll();
        List<Task> listaParcial = new ArrayList<Task>();
        for (Task task : listaCompleta) {
            if (task.getEstado().equals(estado)) {
                listaParcial.add(task);
            }
        }
        return listaParcial;
    }
}
