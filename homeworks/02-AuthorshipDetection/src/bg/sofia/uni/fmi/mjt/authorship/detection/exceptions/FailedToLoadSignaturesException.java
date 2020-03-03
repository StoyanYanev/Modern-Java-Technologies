package bg.sofia.uni.fmi.mjt.authorship.detection.exceptions;

public class FailedToLoadSignaturesException extends Exception {

    public FailedToLoadSignaturesException(String message) {
        super(message);
    }

    public FailedToLoadSignaturesException(String message, Throwable cause) {
        super(message, cause);
    }
}
