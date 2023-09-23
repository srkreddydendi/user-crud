package com.synthesis.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synthesis.entities.User;

@Repository
public interface SynthesisRepository extends JpaRepository<User, Long> {

	List<User> findByFirstNameAndLastName(String firstName, String lastName);

}