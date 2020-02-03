package ru.valerykorzh.springdemo.controller.exception;

public class AnswerNotFoundException extends RuntimeException {

    private static final String ANSWER_FOT_FOUND = "Answer with id %d not found";

    private String message;

    public AnswerNotFoundException(Long id) {
        super(String.format(ANSWER_FOT_FOUND, id));
        message = String.format(ANSWER_FOT_FOUND, id);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
