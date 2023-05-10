package com.example.message.services;

import com.example.message.Repository.UserRepository;
import com.example.message.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public boolean userExist(int id){
        return userRepository.existsById(id);
    }
    public User findUser(int id){
        return userRepository.findById(id).get();
    }




}
