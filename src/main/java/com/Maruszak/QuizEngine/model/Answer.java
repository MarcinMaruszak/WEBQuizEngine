package com.Maruszak.QuizEngine.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Answer {
    private int[] answer;

    public Answer() {
    }

    public Answer(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;


        if (answer1.answer != null) {
            Arrays.sort(answer1.answer);
            if(answer1.answer.length==0){
                answer1.answer=null;
            }
        }
        if (answer != null) {
            Arrays.sort(answer);
            if(answer.length==0){
                answer=null;
            }
        }
        return Arrays.equals(answer, answer1.answer);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(answer);
    }
}
