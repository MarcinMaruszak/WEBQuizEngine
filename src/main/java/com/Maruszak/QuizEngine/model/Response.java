package com.Maruszak.QuizEngine.model;

import org.springframework.stereotype.Component;

@Component
public class Response {
    private boolean success;
    private String feedback;

    public Response() { }

    public Response(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
