package com.synthesis.controller;

import com.synthesis.entities.User;
import com.synthesis.service.SynthesisService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SynthesisControllerTest {

    @Mock
    private SynthesisService synthesisService;

    @InjectMocks
    private SynthesisController synthesisController;

    private User preapreUser() {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setFirstName("Alex");
        return user;
    }

    @Test
    public void ping() {
        Assertions.assertEquals(synthesisController.ping() ,"pong");
    }

    @Test
    public void createUser() {

        String status = synthesisService.createUser(preapreUser());
        Assertions.assertNotNull(status);
    }

    @Test
    public void getUser() {
        Mockito.when(synthesisService.getUser(Long.valueOf(1))).thenReturn(preapreUser());
        User user = synthesisController.getUser(Long.valueOf(1));
        Assertions.assertNotNull(user);
    }

    @Test
    public void deleteUser(@PathVariable(name = "userId") Long userId) {
        synthesisService.deleteUser(userId);
    }

    @Test
    public void updateUser(@RequestBody User user) {
        String status = synthesisService.updateUser(user);
        Assertions.assertNotNull(status);
    }

    @Test
    public void getUsers(@PathVariable(name = "fname") String firstName, @PathVariable(name = "lname") String lastName) {
        List<User> users =  synthesisService.getUsers(firstName, lastName);
        Assertions.assertNotNull(users);
    }

    @Test
    public void getAllUsers() {
        List<User> users = synthesisService.getAllUsers();
        Assertions.assertNotNull(users);
    }

}
