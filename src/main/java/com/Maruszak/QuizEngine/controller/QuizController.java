package com.Maruszak.QuizEngine.controller;

import com.Maruszak.QuizEngine.model.Answer;
import com.Maruszak.QuizEngine.model.Quiz;
import com.Maruszak.QuizEngine.model.Response;
import com.Maruszak.QuizEngine.services.QuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class QuizController {
    @Autowired
    QuizServiceImpl quizService;



    public QuizController() {
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,10);
        return quizService.findAll(pageable);
    }

    @PostMapping(path = "/api/quizzes")
    public Quiz addQuiz(@RequestBody @Valid Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    @GetMapping(path = "api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Response solve(@PathVariable long id, @RequestBody Answer answerObject) {
        return quizService.solve(id, answerObject);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public void delete(@PathVariable long id) {
        quizService.delete(id);
    }
}
