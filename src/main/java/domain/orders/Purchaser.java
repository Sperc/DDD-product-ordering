package domain.orders;

import lombok.Value;

@Value
public class Purchaser {
    private PurchaserId purchaserId;
    private String email;
    private Type type;

    public enum Type {
        PREMIUM,
        ORDINARY
    }
}
