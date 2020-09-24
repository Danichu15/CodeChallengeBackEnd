package com.codeChallenge.demo.data.address;

import org.springframework.data.jpa.repository.JpaRepository;

//Repository needed to have the entity address represented as a table in the data base.

public interface AddressRepository extends JpaRepository<Address, Integer>{

	Address findBystreet(String street);
	
	Address findByid(Integer id);
	
}
