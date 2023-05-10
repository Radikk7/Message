package com.example.message.Repository;


import com.example.message.models.Idea;
import com.example.message.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
   boolean existsByLogin(String Login);
   boolean existsByLoginAndPassword(String login,String password);
   User findByLoginAndPassword(String login,String password);
   boolean findByLogin(String login);
   boolean existsByLikesContains(Idea idea);
  // public List<Idea> findByIdAndLikesContains(int id);
   boolean existsById(int id);


}
