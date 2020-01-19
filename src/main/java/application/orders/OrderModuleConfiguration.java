package application.orders;

import domain.orders.*;
import domain.shared.UuidGenerator;

import java.time.Clock;
import java.util.List;
import java.util.Map;

public class OrderModuleConfiguration {

    public OrderApplicationService orderApplicationService() {
        return new OrderApplicationService(orderDomainService(orderRepository(),
                                                              new OrderNotificationImpl(),
                                                              new UuidGenerator(),
                                                              Clock.systemDefaultZone()),
                                           getPurchaserSupplier(),
                                           getProductSupplier());
    }

    public OrderApplicationService OrderApplicationServiceForTest(OrderRepository orderRepository,
                                                                  OrderNotificationImpl orderNotification,
                                                                  UuidGenerator uuidGenerator,
                                                                  Clock clock,
                                                                  PurchaserSupplier purchaserSupplier,
                                                                  ProductSupplier productSupplier) {
        return new OrderApplicationService(orderDomainService(orderRepository, orderNotification, uuidGenerator, clock),
                                           purchaserSupplier,
                                           productSupplier);
    }

    public OrdersDomainService orderDomainService(OrderRepository orderRepository,
                                                  OrderNotificationImpl orderNotification,
                                                  UuidGenerator uuidGenerator,
                                                  Clock clock) {
        return new OrdersDomainService(orderRepository,
                                       orderFactory(orderNotification, uuidGenerator, clock));
    }

    private OrderFactory orderFactory(OrderNotificationImpl orderNotification, UuidGenerator uuidGenerator, Clock clock) {
        return new OrderFactory(clock,
                                uuidGenerator,
                                orderNotification,
                                getDiscountStrategyFactory()
        );
    }

    private ProductMockSupplier getProductSupplier() {
        return new ProductMockSupplier(Map.of(
                "1", new ProductContext(ProductId.of("1"), 1223L),
                "2", new ProductContext(ProductId.of("2"), 23L),
                "3", new ProductContext(ProductId.of("3"), 13L),
                "4", new ProductContext(ProductId.of("4"), 1L),
                "5", new ProductContext(ProductId.of("5"), 63L)
                                             ));
    }

    private PurchaserMockSupplier getPurchaserSupplier() {
        return new PurchaserMockSupplier(Map.of(
                "1", new Purchaser(PurchaserId.of("1"), "email@test.pl", Purchaser.Type.ORDINARY),
                "2", new Purchaser(PurchaserId.of("2"), "email2@test.pl", Purchaser.Type.ORDINARY),
                "3", new Purchaser(PurchaserId.of("3"), "email3@test.pl", Purchaser.Type.PREMIUM),
                "4", new Purchaser(PurchaserId.of("4"), "email4@test.pl", Purchaser.Type.PREMIUM))
        );
    }

    private DiscountStrategyFactory getDiscountStrategyFactory() {
        return new DiscountStrategyFactory(List.of(new PremiumUserDiscount(),
                                                   new OrdinaryUserDiscount()));
    }

    private OrderRepository orderRepository() {
        return new OrderRepositoryImpl();
    }
}
