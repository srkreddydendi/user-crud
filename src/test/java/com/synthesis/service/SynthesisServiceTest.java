package com.synthesis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.entities.User;
import com.synthesis.repository.SynthesisRepository;
import com.synthesis.util.SynthesisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
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

    @Spy
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(synthesisService, "endpoint", "");
    }

    @Test
    public void createUser() {
        User user = SynthesisUtil.preapreUser();
        Mockito.doNothing().when(queueMessagingTemplate).send(Mockito.anyString(), Mockito.any());
        Mockito.when(synthesisRepository.save(user)).thenReturn(user);
        String response = synthesisService.createUser(user);
        Assertions.assertNotNull(response);
    }

    @Test
    public void getUser() {
        Optional<User> optionalUser = Optional.of(SynthesisUtil.preapreUser());
        Mockito.when(synthesisRepository.findById(Mockito.any())).thenReturn(optionalUser);
        User user = synthesisService.getUser(Long.valueOf(1));
        Assertions.assertNotNull(user);

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
        Assertions.assertNotNull(response);
    }

    @Test
    public void getUsers() {
        Mockito.when(synthesisRepository.findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Arrays.asList(SynthesisUtil.preapreUser()));
        List<User> users = synthesisService.getUsers("test", "test");
        Assertions.assertNotNull(users);
    }

    @Test
    public void getAllUsers() {
        Mockito.when(synthesisRepository.findAll()).thenReturn(Arrays.asList(SynthesisUtil.preapreUser()));
        List<User> users = synthesisService.getAllUsers();
        Assertions.assertNotNull(users);
    }


}
