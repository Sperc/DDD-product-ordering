package domain.shared;

import lombok.Value;

@Value
public class OrderException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final String message;
}
