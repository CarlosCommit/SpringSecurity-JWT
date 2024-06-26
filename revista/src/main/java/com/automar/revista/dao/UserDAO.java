package com.automar.revista.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automar.revista.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
}
