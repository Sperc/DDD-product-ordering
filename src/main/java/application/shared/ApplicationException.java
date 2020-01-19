package application.shared;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends RuntimeException {
    private final ApplicationError errorStatus;
    private final String message;
}
