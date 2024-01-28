package Niji.Backend.Assessment.Mintyn.Exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class CommonException extends Exception{

    private final HttpStatus httpStatus;

    public CommonException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = INTERNAL_SERVER_ERROR;
    }
}
