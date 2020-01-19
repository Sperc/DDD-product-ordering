package application.orders;

import domain.orders.Purchaser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PurchaserMockSupplier implements PurchaserSupplier {
    private Map<String, Purchaser> playerList;

    @Override
    public Optional<Purchaser> supply(String id) {
        return Optional.ofNullable(playerList.get(id));
    }
}
