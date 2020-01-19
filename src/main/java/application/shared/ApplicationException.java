package application.shared;

import lombok.Value;

@Value
public class ApplicationException extends RuntimeException {
    private final ApplicationError errorStatus;
    private final String message;
}
