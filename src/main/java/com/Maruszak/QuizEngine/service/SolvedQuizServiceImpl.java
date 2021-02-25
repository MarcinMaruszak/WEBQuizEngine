package com.Maruszak.QuizEngine.service;

import com.Maruszak.QuizEngine.model.SolvedQuiz;
import com.Maruszak.QuizEngine.model.User;
import com.Maruszak.QuizEngine.repository.SolvedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SolvedQuizServiceImpl implements SolvedQuizService{

    @Autowired
    SolvedQuizRepository solvedQuizRepository;

    @Autowired
    UserDetailsServiceImpl userService;


    @Override
    public Page<SolvedQuiz> getCompleted(int page) {
        String userName =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(userName);
        Pageable pageable = PageRequest.of(page, 10);
        return solvedQuizRepository.findByUserOrderByCompletedAtDesc(user, pageable);

    }

    @Override
    public void save(SolvedQuiz solvedQuiz) {
        solvedQuizRepository.save(solvedQuiz);
    }
}
