package com.Maruszak.QuizEngine.controller;

import com.Maruszak.QuizEngine.model.SolvedQuiz;
import com.Maruszak.QuizEngine.services.SolvedQuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SolvedQuizController {

    @Autowired
    SolvedQuizServiceImpl solvedQuizService;


    @GetMapping(path = "api/quizzes/completed")
    public Page<SolvedQuiz> getCompleted(@RequestParam(defaultValue = "0") Integer page){

        return solvedQuizService.getCompleted(page);
    }


}
