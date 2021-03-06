package domain.orders;

import domain.shared.ErrorStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductException extends RuntimeException {
    private ErrorStatus status;
    private String message;
}
