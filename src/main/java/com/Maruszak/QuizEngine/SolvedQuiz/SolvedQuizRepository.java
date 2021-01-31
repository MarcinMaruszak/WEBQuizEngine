package com.Maruszak.QuizEngine.SolvedQuiz;

import com.Maruszak.QuizEngine.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    @Query("SELECT s FROM SolvedQuiz s WHERE s.user =:user ORDER BY completedAt DESC")
    Page<SolvedQuiz> getCompleted(
            @Param("user") User user, Pageable pageable);

}
