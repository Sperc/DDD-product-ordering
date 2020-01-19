package application.orders.dto;

import lombok.Value;

@Value
public class AddDeliveryAddressRequest {
    private String purchaserId;
    private String streetName;
    private String city;
    private String firstName;
    private String lastName;
}
