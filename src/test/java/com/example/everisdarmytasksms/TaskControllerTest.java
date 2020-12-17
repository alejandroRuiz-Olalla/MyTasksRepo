package com.example.everisdarmytasksms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.everisdarmytasksms.controller.TasksController;
import com.example.everisdarmytasksms.modelo.Task;
import com.example.everisdarmytasksms.service.TaskService;

@WebMvcTest(TasksController.class)
class TaskControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TaskService tasksService;

	@Test
	public void Tasks_All() throws Exception{
		when(tasksService.findAll()).thenReturn(
				Arrays.asList(new Task((long)2,"task","Ya esta hecho","Completada"),
						new Task((long)3,"Tercera tarea y ultima de prueba","Acabar de diseñar todo","Pendiente"),
						new Task((long)4,"Segunda tarea","","Completada"))
				);
		RequestBuilder request = MockMvcRequestBuilders
				.get("/tasks")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{'id':2,'firt_name':'task','task_description':'Ya esta hecho','estado':'Completada'},{'id':3,'firt_name':'Tercera tarea y ultima de prueba','task_description':'Acabar de diseñar todo','estado':'Pendiente'},{'id':4,'firt_name':'Segunda tarea','task_description':'','estado':'Completada'}]"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Id() throws Exception{
		Task esperado = new Task((long)2,"task","Ya esta hecho","Completada");
		Optional<Task> task = Optional.of(esperado);
		when(tasksService.findById((long)2)).thenReturn(task);
		RequestBuilder request = MockMvcRequestBuilders
				.get("/tasks/buscarPorID/2")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':2,'firt_name':'task','task_description':'Ya esta hecho','estado':'Completada'}"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Create() throws Exception{
		Task esperado = new Task((long)6,"Quinta tarea","jklñasdfjklñjklñasdf","Pendiente");
		when(tasksService.saveByEntity(Mockito.any(Task.class))).thenReturn(esperado);
		RequestBuilder request = MockMvcRequestBuilders
				.post("http://localhost:8080/tasks/insert")
				.content("{\"id\":6,\"firt_name\":\"Quinta tarea\",\"task_description\":\"jklñasdfjklñjklñasdf\",\"estado\":\"Pendiente\"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':6,'firt_name':'Quinta tarea','task_description':'jklñasdfjklñjklñasdf','estado':'Pendiente'}"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Delete() throws Exception{
		Task esperado = new Task((long)2,"task","Ya esta hecho","Completada");
		Optional<Task> task = Optional.of(esperado);
		when(tasksService.findById((long)2)).thenReturn(task);
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/tasks/2")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':2,'firt_name':'task','task_description':'Ya esta hecho','estado':'Completada'}"))
				.andReturn();
	}
	
//	@Test
//	public void Tasks_() throws Exception{
//		RequestBuilder request = MockMvcRequestBuilders
//				.get("/tasks")
//				.accept(MediaType.APPLICATION_JSON);
//		
//		MvcResult result = mockMvc.perform(request)
//				.andExpect(status().isOk())
//				.andExpect(content().string("Hello World"))
//				.andReturn();
//		fail("Not yet implemented");
//	}

}
