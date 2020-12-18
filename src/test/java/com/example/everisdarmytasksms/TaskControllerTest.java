package com.example.everisdarmytasksms;


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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
				Arrays.asList(new Task((long)1,"Primera tarea","Descripcion de prueba","Pendiente"),
						new Task((long)2,"Segunda tarea","","Completada"),
						new Task((long)4,"task5","task5","Pendiente"))
				);
		RequestBuilder request = MockMvcRequestBuilders
				.get("/tasks")
				.accept(MediaType.APPLICATION_JSON);
		
		/*MvcResult result = */mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[\r\n" + 
						"    {\r\n" + 
						"        \"id\": 1,\r\n" + 
						"        \"first_name\": \"Primera tarea\",\r\n" + 
						"        \"task_description\": \"Descripcion de prueba\",\r\n" + 
						"        \"estado\": \"Pendiente\"\r\n" + 
						"    },\r\n" + 
						"    {\r\n" + 
						"        \"id\": 2,\r\n" + 
						"        \"first_name\": \"Segunda tarea\",\r\n" + 
						"        \"task_description\": \"\",\r\n" + 
						"        \"estado\": \"Completada\"\r\n" + 
						"    },\r\n" + 
						"    {\r\n" + 
						"        \"id\": 4,\r\n" + 
						"        \"first_name\": \"task5\",\r\n" + 
						"        \"task_description\": \"task5\",\r\n" + 
						"        \"estado\": \"Pendiente\"\r\n" + 
						"    }\r\n" + 
						"]"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Id() throws Exception{
		Task esperado = new Task((long)2,"Segunda tarea","","Completada");
		Optional<Task> task = Optional.of(esperado);
		when(tasksService.findById((long)2)).thenReturn(task);
		RequestBuilder request = MockMvcRequestBuilders
				.get("/task/2")
				.accept(MediaType.APPLICATION_JSON);
		
		/*MvcResult result = */mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':2,'first_name':'Segunda tarea','task_description':'','estado':'Completada'}"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Create() throws Exception{
		Task esperado = new Task((long)6,"Quinta tarea","jklñasdfjklñjklñasdf","Pendiente");
		when(tasksService.saveByEntity(Mockito.any(Task.class))).thenReturn(esperado);
		RequestBuilder request = MockMvcRequestBuilders
				.post("http://localhost:8080/task")
				.content("{\"id\":6,\"first_name\":\"Quinta tarea\",\"task_description\":\"jklñasdfjklñjklñasdf\",\"estado\":\"Pendiente\"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		
		/*MvcResult result = */mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':6,'first_name':'Quinta tarea','task_description':'jklñasdfjklñjklñasdf','estado':'Pendiente'}"))
				.andReturn();
	}
	
	@Test
	public void Tasks_Delete() throws Exception{
		Task esperado = new Task((long)2,"Segunda tarea","","Completada");
		Optional<Task> task = Optional.of(esperado);
		when(tasksService.deleteById((long)2)).thenReturn(task);
		RequestBuilder request = MockMvcRequestBuilders
				.delete("/task/2")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{'id':2,'first_name':'Segunda tarea','task_description':'','estado':'Completada'}"))
				.andReturn();
	}
}
