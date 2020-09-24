package com.codeChallenge.demo.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;

//Controller where the requests from the client are redirected to their corresponding methods in the server.

@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	public UserRestController() {
		
	}
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<Object>getUsers(){
		//Return a list with all the users in the data base:
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/createUsers", method = RequestMethod.POST)
	public ResponseEntity<Object>createUser(@RequestBody User user){
		//Create a new user and get the result:
		User result = userService.createUsers(user);
		
		if(result != null) {
			//If the user was created successfully return code 201 and the new user:
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}else {
			//If there was an error return code 405:
			return new ResponseEntity<>("Invaliad input", HttpStatus.METHOD_NOT_ALLOWED);
		}
	}
	
	@RequestMapping(value = "/getUsersById/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Object>getUserById(@PathVariable Integer userId){
		//Check if the id used in the URL is an Integer:
		if(userId instanceof Integer) {
			//Get the result of finding the user:
			User result = userService.getUsersById(userId);
			
			if(result != null) {
				//If the user was found successfully return code 200 and the user:
				return new ResponseEntity<Object>(result, HttpStatus.OK);
			}else {
				//If the user was not found return code 404:
				return new ResponseEntity<Object>("User not found", HttpStatus.NOT_FOUND);
			}
		}else {
			//If the id number used to find the user is not an Integer return code 400:
			return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/updateUsersById/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<Object>updateUserById(@PathVariable Integer userId, @RequestBody User user){
		//Check if the id used in the URL is an Integer:
		if(userId instanceof Integer) {
			//Get the result of updating the user:
			User result = userService.updateUserById(userId, user);
			
			if(result != null) {
				//If the user was updated successfully return code 200 and the updated user:
				return new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				//If the user was not found return code 404:
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
			}
			
		}else {
			//If the id number used to find the user is not an Integer return code 400:
			return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/deleteUsersById/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object>deleteUserById(@PathVariable Integer userId){
		//Check if the id used in the URL is an Integer:
		if(userId instanceof Integer) {
			//Check if user was successfully deleted:
			if(userService.deleteUserById(userId)) {
				//If the user was deleted return code 200:
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}else {
				//If the user was not found return code 404:
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
			}
		}else {
			//If the id number used to find the user is not an Integer return code 400:
			return new ResponseEntity<>("Invalid user id", HttpStatus.BAD_REQUEST);
		}
	}
	
}
