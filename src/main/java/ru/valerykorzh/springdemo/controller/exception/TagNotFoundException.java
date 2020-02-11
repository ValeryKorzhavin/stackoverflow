package ru.valerykorzh.springdemo.controller.exception;

public class TagNotFoundException extends RuntimeException {

    private static final String TAG_ID_NOT_FOUND = "Tag with id %d not found";
    private static final String TAG_NAME_NOT_FOUND = "Tag with name %s not found";

    private String message;

    public TagNotFoundException(Long id) {
        super(String.format(TAG_ID_NOT_FOUND, id));
        message = String.format(TAG_ID_NOT_FOUND, id);
    }

    public TagNotFoundException(String name) {
        super(String.format(TAG_NAME_NOT_FOUND, name));
        this.message = String.format(TAG_NAME_NOT_FOUND, name);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
