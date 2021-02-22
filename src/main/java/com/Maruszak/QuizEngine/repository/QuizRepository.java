package com.Maruszak.QuizEngine.repository;

import com.Maruszak.QuizEngine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
