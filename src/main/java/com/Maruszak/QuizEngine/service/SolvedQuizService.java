package com.Maruszak.QuizEngine.service;

import com.Maruszak.QuizEngine.model.SolvedQuiz;
import org.springframework.data.domain.Page;

public interface SolvedQuizService {

    Page<SolvedQuiz> getCompleted(int page);

    void save(SolvedQuiz solvedQuiz);
}
