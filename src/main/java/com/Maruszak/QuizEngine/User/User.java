package com.Maruszak.QuizEngine.User;

import com.Maruszak.QuizEngine.Quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Entity
public class User implements UserDetails {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @Pattern(regexp = "\\w+?@\\w+?\\.\\w+?")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    @NotNull
    @Size(min=5)
    private String password;

    @JsonIgnore
    @Column
    private GrantedAuthority [] role;

    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @ElementCollection(targetClass= Quiz.class)
    private List<Quiz> quizzes = new ArrayList<>();

    public User() {
    }

    public User(long id, String email, String password,
                GrantedAuthority[] role, List<Quiz> quizzes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.quizzes = quizzes;
    }

    public long getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Arrays.asList(role));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public GrantedAuthority[] getRole() {
        return role;
    }

    public void setRole(GrantedAuthority[] role) {
        this.role = role;
    }

    public Collection<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
