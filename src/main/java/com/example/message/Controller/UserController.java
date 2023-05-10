package com.example.message.Controller;

import com.example.message.Repository.UserRepository;
import com.example.message.models.Idea;
import com.example.message.models.User;
import com.example.message.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IdeaService ideaService;


    @PostMapping("/user")
    public ResponseEntity<?> user(@RequestParam(name = "userName")String userName,@RequestParam(name = "userLogin")String userLogin,
                                  @RequestParam(name = "editTextTextPassword")String editTextTextPassword,@RequestParam(name = "userPhone")String userPhone) {
            User user = new User();
            user.setName(userName);
            user.setLogin(userLogin);
            user.setPassword(editTextTextPassword);
            user.setPhone(userPhone);

        if (userRepository.existsByLogin(user.getLogin())){
           return new ResponseEntity<>("User already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userRepository.save(user);
       return new ResponseEntity<>("The user is successfully added",HttpStatus.OK);
    }
    @PostMapping("/userauAuthorization")
    public ResponseEntity<?> authorization(@RequestParam (name = "login") String login ,
                                           @RequestParam (name = "password") String password){
        if (login.isEmpty()){
            return new ResponseEntity<>("Login ist null",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Password ist null",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }

        if (userRepository.existsByLoginAndPassword(login,password)){
        User user=userRepository.findByLoginAndPassword(login,password);
        return new ResponseEntity<>(user.getId(),HttpStatus.OK);

        }
           if (userRepository.existsByLogin(login)){
               return new ResponseEntity<>("Incorrect password",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
           }
            if (!userRepository.existsByLogin(login)){
                return new ResponseEntity<>("such a user was not found",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
            else {
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
            }
    }
    @PostMapping("/authoridea")
    public ResponseEntity<?>authoridea(@RequestParam (name = "userId")String userId){
        if (!userRepository.existsById(Integer.parseInt(userId))){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        User user=userRepository.findById(Integer.valueOf(userId)).get();
        List<Idea> ideaList= ideaService.authorIdea(user);

        if (ideaList.size() <=0){
            return new ResponseEntity<>("The user has no ideas",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        else {
            return new ResponseEntity<>(ideaList,HttpStatus.OK);
        }
    }
    @PostMapping("/favoritesIdea")
    public ResponseEntity<?> favoritesIdea(@RequestParam (name = "userId")String userId){
        if (!userRepository.existsById(Integer.parseInt(userId))){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        User user=userRepository.findById(Integer.valueOf(userId)).get();
        List<Idea>ideaList= ideaService.favoritesIdea(user);
        if (ideaList.size() <=0){
            return new ResponseEntity<>("The user has no ideas",HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        else {
            return new ResponseEntity<>(ideaList,HttpStatus.OK);
        }


    }



}
