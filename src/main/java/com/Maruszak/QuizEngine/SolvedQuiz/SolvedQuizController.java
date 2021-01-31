package com.Maruszak.QuizEngine.SolvedQuiz;

import com.Maruszak.QuizEngine.User.User;
import com.Maruszak.QuizEngine.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SolvedQuizController {
    @Autowired
    SolvedQuizRepository solvedQuizRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping(path = "api/quizzes/completed")
    public Page<SolvedQuiz> getCompleted(@RequestParam(defaultValue = "0") Integer page){
        String userName =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userName);
        Pageable pageable = PageRequest.of(page, 10);
        return solvedQuizRepository.getCompleted(user, pageable);
    }


}
