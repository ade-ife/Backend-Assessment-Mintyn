package Niji.Backend.Assessment.Mintyn.Exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CardNotFoundException extends RuntimeException{

    private final HttpStatus httpStatus;

    public CardNotFoundException(String message) {
        super(message);
        httpStatus = BAD_REQUEST;
    }
}
