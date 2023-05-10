package com.example.message.services;

import com.example.message.Repository.IdeaRepository;
import com.example.message.Repository.UserRepository;
import com.example.message.models.Idea;
import com.example.message.models.User;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IdeaService {
    @Autowired
    IdeaRepository ideaRepository;
    @Autowired
    UserRepository userRepository;

    public boolean ideaExist(int id) {
        return ideaRepository.existsById(id);
    }

    public boolean likeExist(User user, Idea idea) {
        return (user.getLikes().contains(idea) && idea.getLikedUsers().contains(user));
    }

    public void countLikes(User user, Idea idea) {
        List<User> userList = idea.getLikedUsers();
        userList.add(user);
        idea.setLikedUsers(userList);
        int likes = idea.getLikes();
        likes = likes + 1;
        idea.setLikes(likes);
        List<Idea> ideaList = user.getLikes();
        ideaList.add(idea);
        user.setLikes(ideaList);
        userRepository.save(user);
        ideaRepository.save(idea);
    }

    public Idea findIdea(int id) {
        return ideaRepository.findById(id).get();
    }

    public boolean checkLike(User user, Idea idea) {
        //проверить есть ли у этого юзера эта идея , а у этой идеи этот юзер
        List<Idea> ideaList = user.getLikes();
        List<Idea> answersList = ideaList.stream().filter(x -> x.equals(idea)).toList();//лист после проверки
        if (answersList.size() == 0) {
            return false;
        }
        List<User> userList = idea.getLikedUsers();
        List<User> userListAnswers = userList.stream().filter(x -> x.equals(user)).toList();
        if (userListAnswers.size() == 0) {
            return false;
        }
        return true;
    }

    public void delete(User user, Idea idea) {
        List<Idea> ideaList = user.getLikes();
        ideaList.remove(idea);
        user.setLikes(ideaList);
        List<User> userList = idea.getLikedUsers();
        userList.remove(user);
        idea.setLikedUsers(userList);
        int count = idea.getLikes();
        count = count - 1;
        idea.setLikes(count);
        userRepository.save(user);
        ideaRepository.save(idea);
    }

  //  public List<Idea> allIdeas(){
//
  //  }


    public List<Idea> getIdeaForUser(User user,List<Idea>ideaList){
        List<Idea>ideaListUser= user.getLikes();//идеи с лайком от юзера
        for (int i = 0; i < ideaList.size();i++) {
            if (ideaListUser.contains(ideaList.get(i))){
             ideaList.get(i).setFavorite(true);
            }
        }
        return ideaList;
    }
    public List<Idea> authorIdea(User user){
        List<Idea>ideaList= ideaRepository.findByAuthor(user);
        return ideaList;
    }
    public List<Idea> favoritesIdea(User user){
       List<Idea>list= user.getLikes();
       return list;
    }
    public Idea redactionIdea(int userId,String title,String description,int ideaId){
        Idea ideaOld= ideaRepository.findByIdAndAuthorId(ideaId,userId);
        ideaOld.setDescription(description);
        ideaOld.setTitle(title);
        Date date = new Date();
        ideaOld.setTimestamp(new Timestamp(date.getTime())); //взял это из функции добавления
        return  ideaRepository.save(ideaOld);
    }

    public void dislike(Idea idea , User user){//если пользователь повторно ставит лайк то это удаляет его лайк с новости
        List<User> ideaListUsers = idea.getLikedUsers();
         ideaListUsers.remove(user);
         idea.setLikedUsers(ideaListUsers);
         ideaRepository.save(idea);
         List<Idea>ideas= user.getLikes();
         ideas.remove(idea);
         user.setLikes(ideas);
         userRepository.save(user);
    }





}
