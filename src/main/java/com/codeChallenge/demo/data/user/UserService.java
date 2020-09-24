package com.codeChallenge.demo.data.user;

import java.util.List;

//Interface where all the usable methods are declared.

public interface UserService {
	
	public abstract List<User> getUsers();
	public abstract User createUsers(User user);
	public abstract User getUsersById(Integer id);
	public abstract User updateUserById(Integer id, User user);
	public abstract boolean deleteUserById(Integer id);
	
}
