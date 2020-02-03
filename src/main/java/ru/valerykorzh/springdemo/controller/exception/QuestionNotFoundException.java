package ru.valerykorzh.springdemo.controller.exception;

public class QuestionNotFoundException extends RuntimeException {

    private static final String QUESTION_FOT_FOUND = "Question with id %d not found";

    private String message;

    public QuestionNotFoundException(Long id) {
        super(String.format(QUESTION_FOT_FOUND, id));
        message = String.format(QUESTION_FOT_FOUND, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
