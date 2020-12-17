package com.example.everisdarmytasksms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.everisdarmytasksms.modelo.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
}
