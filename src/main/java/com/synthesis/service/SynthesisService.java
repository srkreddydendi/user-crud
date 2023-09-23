package com.synthesis.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.repository.SynthesisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.synthesis.entities.User;

@Service
public class SynthesisService {

	private SynthesisRepository synthesisRepository;

	private QueueMessagingTemplate queueMessagingTemplate;

	private String endpoint;

	ObjectMapper objectMapper;

	
	@Autowired
	public SynthesisService(SynthesisRepository synthesisRepository,
							QueueMessagingTemplate queueMessagingTemplate,
							@Value("${cloud.aws.end-point.uri}") String endpoint,
							ObjectMapper objectMapper
							) {
		this.synthesisRepository = synthesisRepository;
		this.queueMessagingTemplate = queueMessagingTemplate;
		this.endpoint = endpoint;
		this.objectMapper = objectMapper;
	}
	
	public String createUser(User user) {
		User user1 = synthesisRepository.save(user);
		pushMsgToSqs(user1, "Created");
		if(user1 != null)
			return "user saved successfully";
		return null;
	}
	
	public User getUser(Long userId) {
		User user = null;
		Optional<User> optionalUser = synthesisRepository.findById(userId);
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
			pushMsgToSqs(user, "Fetched");
		}
		return user;
	}

	public void deleteUser(Long userId) {
		synthesisRepository.deleteById(userId);
	}

	public String updateUser(User user) {
		User updatedUser = synthesisRepository.save(user);
		if(updatedUser != null) {
			pushMsgToSqs(updatedUser, "Fetched");
			return "user updated successfully";
		}
		return null;
	}
	
	public List<User> getUsers(String fname, String lname) {
		return synthesisRepository.findByFirstNameAndLastName(fname, lname);
	}

	public List<User> getAllUsers() {
		return synthesisRepository.findAll();
	}
	private void pushMsgToSqs(Object object, String operation) {
		try {
			String msg = objectMapper.writeValueAsString(object);
			queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(msg.concat(" ").concat(operation)).build());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

	}
}
