package com.Maruszak.QuizEngine.Quiz;

import com.Maruszak.QuizEngine.SolvedQuiz.SolvedQuiz;
import com.Maruszak.QuizEngine.SolvedQuiz.SolvedQuizRepository;
import com.Maruszak.QuizEngine.User.User;
import com.Maruszak.QuizEngine.User.UserRepository;
import com.Maruszak.QuizEngine.domain.Answer;
import com.Maruszak.QuizEngine.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@RestController
public class QuizController {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SolvedQuizRepository solvedQuizRepository;


    public QuizController() {
    }


    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,10);
        return quizRepository.findAll(pageable);

    }

    @PostMapping(path = "/api/quizzes")
    public Quiz addQuiz(@RequestBody @Valid Quiz quiz) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(username);
        quiz.setUser(user);
        quizRepository.save(quiz);
        return quiz;
    }

    @GetMapping(path = "api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Response solve(@PathVariable long id, @RequestBody Answer answerObject) {
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
            User user = userRepository.findByEmail(userName);
            SolvedQuiz solvedQuiz = new SolvedQuiz();
            solvedQuiz.setId(quiz.getId());
            solvedQuiz.setCompletedAt(LocalDateTime.now());
            solvedQuiz.setUser(user);
            solvedQuizRepository.save(solvedQuiz);

            return new Response(true, "Congratulations, you're right!");
        }
        return new Response(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public void delete(@PathVariable long id) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (quiz != null) {
            User user = userRepository.findByEmail(username);
            if (quizRepository.getOne(id).getUser().equals(user)) {
                quizRepository.deleteById(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not an author of quiz, cannot delete");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
