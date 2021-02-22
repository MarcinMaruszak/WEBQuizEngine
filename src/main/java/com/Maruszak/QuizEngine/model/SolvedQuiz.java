package com.Maruszak.QuizEngine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
@Entity
public class SolvedQuiz {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long idCompleted;


    @Column
    private long id;


    @Column
    private LocalDateTime completedAt;

    @JsonIgnore
    @OneToOne
    private User user;

    public SolvedQuiz() {
    }

    public SolvedQuiz(long idCompleted, long id, LocalDateTime completionTime, User userid) {
        this.idCompleted = idCompleted;
        this.id = id;
        this.completedAt = completionTime;
        this.user = userid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
