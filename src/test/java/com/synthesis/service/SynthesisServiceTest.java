package com.synthesis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.entities.User;
import com.synthesis.repository.SynthesisRepository;
import com.synthesis.util.SynthesisUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SynthesisServiceTest {
    @InjectMocks
    private SynthesisService synthesisService;
    @Mock
    private SynthesisRepository synthesisRepository;
    @Mock
    private QueueMessagingTemplate queueMessagingTemplate;
    @Mock
    private String endpoint;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void createUser() {
        User user = SynthesisUtil.preapreUser();
        Mockito.when(synthesisRepository.save(user)).thenReturn(user);
        String response = synthesisService.createUser(user);
    }
    @Test
    public void getUser() {
        Optional<User> optionalUser = Optional.of(SynthesisUtil.preapreUser());
        Mockito.when(synthesisRepository.findById(Mockito.any())).thenReturn(optionalUser);
        User user = synthesisService.getUser(Long.valueOf(1));
    }
    @Test
    public void deleteUser() {
        synthesisRepository.deleteById(Long.valueOf(1));
    }
    @Test
    public void updateUser() {
        User user = SynthesisUtil.preapreUser();
        Mockito.when(synthesisRepository.save(Mockito.any())).thenReturn(user);
        String response = synthesisService.updateUser(user);
    }
    @Test
    public void getUsers() {
        //return synthesisRepository.findByFirstNameAndLastName(fname, lname);
        synthesisService.getUsers("","");
    }
    @Test
    public void getAllUsers() {
         //synthesisRepository.findAll();
    }


}
