package com.Maruszak.QuizEngine.services;

import com.Maruszak.QuizEngine.model.Answer;
import com.Maruszak.QuizEngine.model.Quiz;
import com.Maruszak.QuizEngine.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {

    Page<Quiz> findAll(Pageable pageable);

    Quiz addQuiz(Quiz quiz);

    Quiz getQuiz(long id);

    Response solve(long id, Answer answerObject);

    void delete(long id);
}
