package Niji.Backend.Assessment.Mintyn.Exception;

public class RateLimitExceededException extends RuntimeException{

    public RateLimitExceededException(String message) {
        super(message);
    }
}
