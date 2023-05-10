package com.example.message.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @JsonIgnore
    private String login;
    @JsonIgnore
    private String password;
    private String phone;
    @ManyToMany
    @JoinTable(
            name = "user_like",//
            joinColumns = @JoinColumn(name = "user_id"),//
            inverseJoinColumns = @JoinColumn(name = "idea_id"))//
            @JsonIgnore
    List<Idea> likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login);
    }

    public User(String name, String login, String password, String phone, List<Idea> likes) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.likes = likes;
    }

    public User(int id, String name, String login, String password, String phone) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
    }
    public User( String name, String login, String password, String phone) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.phone = phone;
    }

    public User() {
    }

    public List<Idea> getLikes() {
        return likes;
    }

    public void setLikes(List<Idea> likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String toString(){
        return getName() + " " + getLogin() + " " + getPassword() + " " + getPhone();
    }




}
