package com.example.demo.model;

import com.example.demo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_7")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "email", nullable = false, unique = true, length = 70)
    private String email;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "is_active",nullable = false)
    private int isActive;

    private User() {
    }

    public static class Builder{
        private String username;
        private String email;
        private String password;
        private int age;
        private Gender gender;
        private int isActive;

        public Builder(String username){
            this.username = username;
            this.isActive = 1;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withGender(Gender gender){
            this.gender = gender;
            return this;
        }

        public Builder isActive(int isActive){
            this.isActive = isActive;
            return this;
        }

        public User build(){
            User user1 = new User();
            user1.username = this.username;
            user1.email = this.email;
            user1.password = this.password;
            user1.age = this.age;
            user1.gender = this.gender;
            user1.isActive = this.isActive;
            return user1;
        }

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
