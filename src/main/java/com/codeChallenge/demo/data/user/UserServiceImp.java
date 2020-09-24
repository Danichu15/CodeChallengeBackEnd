package com.codeChallenge.demo.data.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeChallenge.demo.data.address.Address;
import com.codeChallenge.demo.data.address.AddressRepository;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public List<User> getUsers() {
		//Call the data base to return a list with all the users:
		return userRepository.findAll();
	}

	@Override
	public User createUsers(User user) {
		
		//Try to create new user and new address:
		try {
			//Check if there is all the needed data from user:
			if(user.getName() != null && user.getEmail() != null && user.getBirthDate() != null) {
			
				//Take the address from the data given from user:
				Address userAddress = user.getAddress();
			
				//Create new address with the data:
				Address newAddress = new Address(userAddress.getStreet(), userAddress.getState(), userAddress.getCity(), userAddress.getCountry(), userAddress.getZip());
			
				//Save the new address in the data base:
				addressRepository.save(newAddress);
			
				//Load the new saved address from data base:
				Address auxAddress = addressRepository.findBystreet(newAddress.getStreet());
			
			
				//Create new user with the data:
				User newUser = new User(user.getName(), user.getEmail(), user.getBirthDate(), auxAddress);
				
				//Save the new user in the data base:
				userRepository.save(newUser);
				
				return newUser;
				
			}else {
				return null;
			}
			
			
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public User getUsersById(Integer id) {
		//Try to call the data base to return an user using the id, if not possible, return null:
		try {
			return userRepository.findByid(id);
		}catch(Exception e) {
			return null;
		}
		
	}

	@Override
	public User updateUserById(Integer id, User user) {
		//Try to call the data base to return an user using the id, if not possible, return null:
		try {
			//Get the user from data base using the given id:
			User userToUpdate = userRepository.findByid(id);
			
			//Update the name if there is a new one:
			if(user.getName() != null) {
				userToUpdate.setName(user.getName());
			}
			//Update the email if there is a new one:
			if(user.getEmail() != null) {
				userToUpdate.setEmail(user.getEmail());
			}
			//Update the birth date if there is a new one:
			if(user.getBirthDate() != null) {
				userToUpdate.setBirthDate(user.getBirthDate());
			}
			
			//Update the address if there is a new one:
			Address newAddress = user.getAddress();
			
			if(newAddress != null) {
				//Get the address from the user using the address id saved in the user data:
				Address addressToUpdate = addressRepository.findByid(userToUpdate.getAddress().getId());
				
				//Update the street if there is a new one:
				if(newAddress.getStreet() != null) {
					addressToUpdate.setStreet(newAddress.getStreet());
				}
				
				//Update the state if there is a new one:
				if(newAddress.getState() != null) {
					addressToUpdate.setState(newAddress.getState());
				}
				
				//Update the city if there is a new one:
				if(newAddress.getCity() != null) {
					addressToUpdate.setCity(newAddress.getCity());
				}
				
				//Update the country if there is a new one:
				if(newAddress.getCountry() != null) {
					addressToUpdate.setCountry(newAddress.getCountry());
				}
				
				//Update the zip if there is a new one:
				if(newAddress.getZip() != null) {
					addressToUpdate.setZip(newAddress.getZip());
				}
				
				//Save the address changes in the data base:
				addressRepository.save(addressToUpdate);
			}
			
			//Save the user changes in the data base;
			userRepository.save(userToUpdate);
			
			return userToUpdate;
			
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteUserById(Integer id) {
		//Try to delete the user in the data base using the id, if not possible, return false:
		try {
			//Get the user to find the corresponding address:
			User userToDelete = userRepository.findByid(id);
			
			//Delete the user from the data base:
			userRepository.deleteById(id);
			
			//Delete the address from the data base:
			addressRepository.deleteById(userToDelete.getAddress().getId());

			return true;
			
		}catch(Exception e) {
			return false;
		}
	}

}
