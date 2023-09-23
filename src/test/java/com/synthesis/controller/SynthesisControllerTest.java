package com.synthesis.controller;

import com.synthesis.entities.User;
import com.synthesis.service.SynthesisService;
import com.synthesis.util.SynthesisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SynthesisControllerTest {

    @Mock
    private SynthesisService synthesisService;

    @InjectMocks
    private SynthesisController synthesisController;



    @Test
    public void ping() {
        Assertions.assertEquals(synthesisController.ping() ,"pong");
    }

    @Test
    public void createUser() {
        Mockito.when(synthesisService.createUser(Mockito.any())).thenReturn("user saved successfully");
        String status = synthesisController.createUser(SynthesisUtil.preapreUser());
        Assertions.assertNotNull(status);
        Assertions.assertEquals(status ,"user saved successfully");
    }

    @Test
    public void getUser() {
        Mockito.when(synthesisService.getUser(Long.valueOf(1))).thenReturn(SynthesisUtil.preapreUser());
        User user = synthesisController.getUser(Long.valueOf(1));
        Assertions.assertNotNull(user);
    }

    @Test
    public void deleteUser() {
        Mockito.doNothing().when(synthesisService).deleteUser(Mockito.anyLong());
        String status = synthesisController.deleteUser(Long.valueOf(1));
        Assertions.assertNotNull(status);
        Assertions.assertEquals("user deleted successfully", status);
    }

    @Test
    public void updateUser() {
        Mockito.when(synthesisService.updateUser(Mockito.any())).thenReturn("Success");
        String status = synthesisController.updateUser(SynthesisUtil.preapreUser());
        Assertions.assertNotNull(status);
        Assertions.assertEquals("Success", status);
    }

    @Test
    public void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(SynthesisUtil.preapreUser());
        Mockito.when(synthesisService.getUsers("test", "test")).thenReturn(users);
        List<User> usersResponse =  synthesisController.getUsers("test", "test");
        Assertions.assertNotNull(usersResponse);
    }

    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(SynthesisUtil.preapreUser());
        Mockito.when(synthesisService.getAllUsers()).thenReturn(users);
        List<User> usersResponse = synthesisController.getAllUsers();
        Assertions.assertNotNull(usersResponse);
    }

}
