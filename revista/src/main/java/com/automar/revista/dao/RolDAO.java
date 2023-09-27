package com.automar.revista.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automar.revista.model.Role;

@Repository
public interface RolDAO extends JpaRepository<Role, Long>{

	Optional<Role> findByNombre(String name); 
	
}
