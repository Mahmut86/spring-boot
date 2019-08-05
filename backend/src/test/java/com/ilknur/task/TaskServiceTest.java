package com.ilknur.task;

import com.ilknur.task.dao.TaskRepository;
import com.ilknur.task.entities.Task;
import com.ilknur.task.entities.User;
import com.ilknur.task.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @Before
    public void beforeTest() {

    }

    @Test
    public void testToSave() {
        Task task = new Task();
        task.setName("Project");

        User user = new User();
        user.setId(1L);
        user.setUsername("ilknur");
        user.setPassword("123");
        user.setAuthenticate(true);

        System.out.print("ss " + taskService);
        //Task resultTask = taskService.save(user, task);
        //assertNotNull(resultTask);


    }
}
