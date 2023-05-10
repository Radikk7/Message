package com.example.message.Controller;

import com.example.message.Repository.IdeaRepository;
import com.example.message.Repository.UserRepository;
import com.example.message.models.Idea;
import com.example.message.models.User;
import com.example.message.services.IdeaService;
import com.example.message.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@RestController
public class IdeaController {
    @Autowired
    IdeaRepository ideaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    IdeaService ideaService;

    @PostMapping("/newidea")
    public ResponseEntity<?> addIdea(@RequestParam(name = "idea_title")String title,@RequestParam(name = "description")String description,
                                     @RequestParam(name = "id") int id) {
        if (ideaRepository.existsByTitle(title)) {
            return new ResponseEntity<>("Idea already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Idea idea = new Idea();
        User user = userRepository.findById(id).get();
        idea.setAuthor(user);
        idea.setTitle(title);
        idea.setDescription(description);
        Date date = new Date();
        idea.setTimestamp(new Timestamp(date.getTime()));
        Idea idea1 = ideaRepository.save(idea);
        return new ResponseEntity<>("The idea is successfully added" + idea1.getId(), HttpStatus.OK);

    }



    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam(name = "userId") int userId, @RequestParam(name = "ideaId") int ideaId) {

        if (!userService.userExist(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!ideaService.ideaExist(ideaId)) {
            return new ResponseEntity<>("Idea not found", HttpStatus.NOT_FOUND);
        }
        User user = userService.findUser(userId);
        Idea idea = ideaService.findIdea(ideaId);

        if (ideaService.likeExist(user, idea)) {
            ideaService.delete(user,idea);//
            return new ResponseEntity<>("Like already delivered", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ideaService.countLikes(user, idea);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
    @PostMapping("/finduser")
    public ResponseEntity<?> findUser(@RequestParam(name = "userId")Integer id){

      User user= userService.findUser(id);
      String name = user.getName();
        return new ResponseEntity<>("OK" + name,HttpStatus.OK);
    }




    @PostMapping("/delete")
    public ResponseEntity<?> deleteLikes(@RequestParam(name = "ideaId") int ideaId, @RequestParam(name = "userId") int userId) {

        if (!userService.userExist(userId) && !ideaService.ideaExist(ideaId)) {
            return new ResponseEntity<>("Invalid request", HttpStatus.NOT_FOUND);
        }
        if (!userService.userExist(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        if (!ideaService.ideaExist(ideaId)) {
            return new ResponseEntity<>("Idea not found", HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findById(userId).get();
        Idea idea = ideaRepository.findById(ideaId).get();
        if (ideaService.checkLike(user, idea)) {
            ideaService.delete(user, idea);//если пользователь повторно ставит лайк то это удаляет его лайк с новости
        } else {
            return new ResponseEntity<>("Enter other details", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);

    }

    @PostMapping("/personals")//
    public ResponseEntity<?> personalData(@RequestParam(name = "userId") int userId) {
        if (!userRepository.existsById(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            List<Idea> list = ideaService.getIdeaForUser(userRepository.findById(userId).get(), ideaRepository.findAll());
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }
    @PostMapping("/redaction")
    public ResponseEntity<?> redactionIdea(@RequestParam(name = "title")String title,@RequestParam(name = "description")String description,
    @RequestParam(name = "userId") int userId, @RequestParam(name = "ideaId") int ideaId) {
        if (!userRepository.existsById(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!ideaService.ideaExist(ideaId)) {
            return new ResponseEntity<>("Idea not found", HttpStatus.NOT_FOUND);
        } else {
            Idea idea1 = ideaService.redactionIdea(userId,title,description,ideaId);
            return new ResponseEntity<>(idea1, HttpStatus.OK);
        }

    }
}
