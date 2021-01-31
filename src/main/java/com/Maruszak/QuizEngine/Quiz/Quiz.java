package com.Maruszak.QuizEngine.Quiz;

import com.Maruszak.QuizEngine.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Component
@Entity
public class Quiz {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @NotEmpty(message = "Title cannot be empty")
    @Column
    private String title;

    @NotEmpty(message = "Text cannot be empty")
    @Column
    private String text;

    @Column
    @NotNull
    @Size(min = 2, message = "Min 2 options required")
    private String[] options;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;


    public Quiz() {
    }

    public Quiz(@NotEmpty(message = "Title cannot be empty") String title,
                  @NotEmpty(message = "Text cannot be empty") String text,
                  @NotNull @Size(min = 2, message = "Min 2 options required")
                        String[] options, int[] answer , User user) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
