package com.Maruszak.QuizEngine.repository;

import com.Maruszak.QuizEngine.model.SolvedQuiz;
import com.Maruszak.QuizEngine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    Page<SolvedQuiz> findByUserOrderByCompletedAtDesc(User user, Pageable pageable);

}
