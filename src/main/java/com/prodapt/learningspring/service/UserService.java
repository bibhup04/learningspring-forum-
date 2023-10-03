package com.prodapt.learningspring.service;

// UserService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        Iterable<User> usersIterable = userRepository.findAll();
        List<User> usersList = new ArrayList<>();
        
        usersIterable.forEach(usersList::add);
        
        return usersList;
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }
}

