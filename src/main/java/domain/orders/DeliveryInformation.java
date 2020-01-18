package domain.orders;

import lombok.Value;

@Value
public class DeliveryInformation {
    private String streetName;
    private String city;
    private String firstName;
    private String lastName;

    public static DeliveryInformation createEmpty() {
        return new DeliveryInformation("", "", "", "");
    }
}
