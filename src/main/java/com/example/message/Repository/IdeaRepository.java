package com.example.message.Repository;

import com.example.message.models.Idea;
import com.example.message.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea,Integer> {
    boolean existsByTitle(String title);
    boolean existsByLikedUsersContains(User user);
   // boolean findByIdAndLikedUsersContains(Idea idea);
    List<Idea> findByAuthor(User user);
    Idea findByIdAndAuthorId(int ideaId,int authorId);


}
