package com.capgemini.food_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.food_app.exception.UserNotFoundException;
import com.capgemini.food_app.model.User;
import com.capgemini.food_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, User updated) {
		User present=userRepository.findById(id).orElseThrow(() ->
		new UserNotFoundException("User with ID " + id + " not found."));

			present.setName(updated.getName());
			present.setEmail(updated.getEmail());
			present.setPassword(updated.getPassword());
			present.setPhone(updated.getPhone());
			present.setUserType(updated.getUserType());
			present.setLocation(updated.getLocation());
			present.setUserImg(updated.getUserImg());
			return userRepository.save(present);

	}

	@Override
	public boolean deleteUser(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public User patchUser(Long id, User patch) {
		// TODO Auto-generated method stub
		User present=userRepository.findById(id).orElseThrow(() ->
		new UserNotFoundException("User with ID " + id + " not found."));

			
			if(patch.getName()!=null)
				present.setName(patch.getName());
			if(patch.getEmail()!=null)
				present.setEmail(patch.getEmail());
			if(patch.getPassword()!=null)
				present.setPassword(patch.getPassword());
			if(patch.getPhone()!=null)
				present.setPhone(patch.getPhone());
			if(patch.getUserType()!=null)
				present.setUserType(patch.getUserType());
			if(patch.getLocation()!=null)
				present.setLocation(patch.getLocation());
			if(patch.getUserImg()!=null)
				present.setUserImg(patch.getUserImg());
			
			return userRepository.save(present);

	}
	
}
