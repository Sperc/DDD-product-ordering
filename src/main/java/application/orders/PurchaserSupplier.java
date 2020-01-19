package application.orders;

import domain.orders.Purchaser;

import java.util.Optional;

public interface PurchaserSupplier {
    Optional<Purchaser> supply(String id);
}
