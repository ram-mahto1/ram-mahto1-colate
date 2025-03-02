package com.example.demo.service;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.exception.GenericException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;


    public User createUser(CreateUserRequest user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(User.builder().email(user.getEmail()).name(user.getName()).mobile(user.getMobile()).build());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
