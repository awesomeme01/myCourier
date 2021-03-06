package com.example.demo.model;

import com.example.demo.enums.CourierStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "authorized_couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private CourierStatus status;
    private int rating;
    @JsonIgnore
    private int prevRating;
    private int feedbackCount;

    private Long pin;

    public Courier() {
    }

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public int getPrevRating() {
        return prevRating;
    }

    public void setPrevRating(int prevRating) {
        this.prevRating = prevRating;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
