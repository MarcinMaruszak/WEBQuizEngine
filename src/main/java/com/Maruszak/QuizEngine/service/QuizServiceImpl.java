package com.Maruszak.QuizEngine.service;

import com.Maruszak.QuizEngine.model.*;
import com.Maruszak.QuizEngine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    SolvedQuizServiceImpl solvedQuizService;

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userService.loadUserByUsername(username);
        quiz.setUser(user);
        quizRepository.save(quiz);
        return quiz;
    }

    @Override
    public Quiz getQuiz(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }

    @Override
    public Response solve(long id, Answer answerObject) {
        int[] answer = answerObject.getAnswer();
        if (answer != null) {
            Arrays.sort(answer);
            if (answer.length == 0) {
                answer = null;
            }
        }
        Quiz quiz = getQuiz(id);
        int[] correctAnswers = quiz.getAnswer();
        if (correctAnswers != null) {
            Arrays.sort(correctAnswers);
            if (correctAnswers.length == 0) {
                correctAnswers = null;
            }
        }
        if (Arrays.equals(answer, correctAnswers)) {
            String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) userService.loadUserByUsername(userName);
            SolvedQuiz solvedQuiz = new SolvedQuiz();
            solvedQuiz.setId(quiz.getId());
            solvedQuiz.setCompletedAt(LocalDateTime.now());
            solvedQuiz.setUser(user);
            solvedQuizService.save(solvedQuiz);

            return new Response(true, "Congratulations, you're right!");
        }
        return new Response(false, "Wrong answer! Please, try again.");
    }

    @Override
    public void delete(long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (quiz != null) {
            User user = (User) userService.loadUserByUsername(username);
            if (quizRepository.getOne(id).getUser().equals(user)) {
                quizRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not an author of a quiz, cannot delete");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
