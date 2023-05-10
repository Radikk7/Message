package com.example.message.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Idea {
    @Id
    @GeneratedValue
    private int id;
    private String title; //строка название/заголовок
    @Size(min = 3, max = 2000000)
    private String description;//строка с длинным описанием
    private int likes; // целое число количество лайков
    private Timestamp timestamp;//дата создания идеи
    private boolean isFavorite;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToMany(mappedBy = "likes")
    @JsonIgnore
    List<User> likedUsers;

    public Idea() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idea idea = (Idea) o;
        return id == idea.id && title.equals(idea.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public Idea(String title, String description, int likes, Timestamp timestamp, boolean isFavorite, User author, List<User> likedUsers) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
        this.author = author;
        this.likedUsers = likedUsers;
    }

    public Idea(String title, String description, Timestamp timestamp, User author) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.author = author;
    }

    public Idea(int id, String title, int likes, Timestamp timestamp, boolean isFavorite, User author) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
        this.author = author;
    }

    public Idea(String title, int likes, Timestamp timestamp, boolean isFavorite, User author) {
        this.title = title;
        this.likes = likes;
        this.timestamp = timestamp;
        this.isFavorite = isFavorite;
        this.author = author;
    }


    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


}
