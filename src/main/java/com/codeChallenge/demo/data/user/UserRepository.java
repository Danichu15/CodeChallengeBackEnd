package com.codeChallenge.demo.data.user;

import org.springframework.data.jpa.repository.JpaRepository;

//Repository needed to have the entity user represented as a table in the data base.

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByid(Integer id); 
	
}
