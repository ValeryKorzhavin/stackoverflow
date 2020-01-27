package ru.valerykorzh.springdemo.controller.exception;

public class AccountNotFoundException extends RuntimeException {

    private static final String ACCOUNT_ID_NOT_FOUND = "Account with id %d not found";
    private static final String ACCOUNT_EMAIL_NOT_FOUND = "Account with email %s not found";

    private String message;

    public AccountNotFoundException(Long id) {
        super(String.format(ACCOUNT_ID_NOT_FOUND, id));
        this.message = String.format(ACCOUNT_ID_NOT_FOUND, id);
    }

    public AccountNotFoundException(String email) {
        super(String.format(ACCOUNT_EMAIL_NOT_FOUND, email));
        this.message = String.format(ACCOUNT_EMAIL_NOT_FOUND, email);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
