package domain.shared;

import java.util.UUID;

public class UuidGenerator {
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
