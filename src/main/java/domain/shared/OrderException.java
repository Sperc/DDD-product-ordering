package domain.shared;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class OrderException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final String message;
}
