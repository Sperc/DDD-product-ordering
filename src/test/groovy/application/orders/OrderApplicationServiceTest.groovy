package application.orders

import application.orders.dto.AddDeliveryAddressRequest
import application.orders.dto.AddProductRequest
import application.orders.dto.CalculateDiscountRequest
import application.orders.dto.CompleteOrderProcessRequest
import application.orders.dto.CreateOrderRequest
import domain.orders.*
import domain.shared.OrderException
import domain.shared.UuidGenerator
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.ZonedDateTime

class OrderApplicationServiceTest extends Specification {
    def orderRepository = new OrderMockRepository()
    def orderNotification = Mock(OrderNotification)
    def orderIdGenerator = Mock(UuidGenerator)
    def purchaserSupplier = Spy(PurchaserSupplier)
    def productSupplier = Spy(ProductSupplier)
    def orderService = new OrderModuleConfiguration().OrderApplicationServiceForTest(orderRepository, orderNotification, orderIdGenerator,
                                                                                     Clock.systemDefaultZone(), purchaserSupplier, productSupplier)


    def "Should create new Order"() {
        given:
        def request = new CreateOrderRequest("1")
        def uuid = UUID.randomUUID().toString()
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      Purchaser.Type.ORDINARY)
        when:
        def response = orderService.createOrder(request)
        then:
        'mock'
        1 * orderIdGenerator.generateUuid() >> uuid
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        'asserts'
        def order = orderRepository.database.get(uuid)
        assert order != null
        assert response.getOrderId() == uuid
        assert order.getId().value == uuid
        assert order.purchaser.purchaserId == purchaser.purchaserId
    }

    def "should throw exception when try to add Product to Order with not exists"() {
        given:
        def request = new AddProductRequest("2", "productId", "purchaserId")
        def product = new ProductContext(ProductId.of(request.productId), 1L)
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      Purchaser.Type.ORDINARY)
        'init database'
        def order = new Order(OrderId.of("1"), ZonedDateTime.now(), new WithoutVerificationProductStrategy(), orderNotification, new OrdinaryUserDiscount(),
                              DeliveryInformation.createEmpty(), OrderStatus.IN_PROGRESS, 0L, purchaser, new ArrayList<Product>())
        orderRepository.database.put(order.getId().value, order)

        when:
        orderService.addProduct(request)
        then:
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        1 * productSupplier.supply(request.productId) >> Optional.of(product)
        thrown(OrderException)
    }

    def "should add product to Order"() {
        given:
        def request = new AddProductRequest("1", "productId", "purchaserId")
        def product = new ProductContext(ProductId.of(request.productId), 1L)
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      Purchaser.Type.ORDINARY)
        'init database'
        def order = new Order(OrderId.of("1"), ZonedDateTime.now(), new WithoutVerificationProductStrategy(), orderNotification, new OrdinaryUserDiscount(),
                              DeliveryInformation.createEmpty(), OrderStatus.IN_PROGRESS, 0L, purchaser, new ArrayList<Product>())
        orderRepository.database.put(order.getId().value, order)

        when:
        orderService.addProduct(request)
        then:
        'mock'
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        1 * productSupplier.supply(request.productId) >> Optional.of(product)
        1 * orderNotification.updateOrderNotification(_, _)
        'assert'
        def orderFromRepository = orderRepository.database.get("1")
        assert orderFromRepository != null
        assert orderFromRepository.products.size() == 1
    }

    @Unroll("For user type: #userType")
    def "should calculate discount"() {
        given:
        def request = new CalculateDiscountRequest("discountCode", "1", "purchaserId")
        def product = new ProductContext(ProductId.of("1"), 100L)
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      userType)
        'init database'
        def order = new Order(OrderId.of("1"), ZonedDateTime.now(), new WithoutVerificationProductStrategy(), orderNotification, discountStrategy,
                              DeliveryInformation.createEmpty(), OrderStatus.IN_PROGRESS, product.price, purchaser, [product])
        orderRepository.database.put(order.getId().value, order)

        when:
        orderService.calculate(request)
        then:
        'mock'
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        'assert'
        def orderFromRepository = orderRepository.database.get("1")
        orderFromRepository.orderPrice == orderPrice

        where:
        userType                | orderPrice | discountStrategy
        Purchaser.Type.ORDINARY | 100L       | new OrdinaryUserDiscount()
        Purchaser.Type.PREMIUM  | 80L        | new PremiumUserDiscount()
    }

    def "AddDeliveryAddress"() {
        given:
        def request = new AddDeliveryAddressRequest("1", "1", "streetName", "Lublin", "firstName", "lastName")
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      Purchaser.Type.ORDINARY)
        'init database'
        def order = new Order(OrderId.of("1"), ZonedDateTime.now(), new WithoutVerificationProductStrategy(), orderNotification, new OrdinaryUserDiscount(),
                              DeliveryInformation.createEmpty(), OrderStatus.IN_PROGRESS, 0L, purchaser, new ArrayList<Product>())
        orderRepository.database.put(order.getId().value, order)

        when:
        orderService.addDeliveryAddress(request)
        then:
        'mock'
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        'assert'
        def orderFromRepository = orderRepository.database.get("1")
        assert orderFromRepository.deliveryInformation.firstName == "firstName"
        assert orderFromRepository.deliveryInformation.lastName == "lastName"
        assert orderFromRepository.deliveryInformation.city == "Lublin"
        assert orderFromRepository.deliveryInformation.streetName == "streetName"

    }

    def "CompleteOrderProcess"() {
        given:
        def request = new CompleteOrderProcessRequest("1", "1")
        def purchaser = new Purchaser(PurchaserId.of(request.purchaserId),
                                      "email@test.pl",
                                      Purchaser.Type.ORDINARY)
        'init database'
        def order = new Order(OrderId.of("1"), ZonedDateTime.now(), new WithoutVerificationProductStrategy(), orderNotification, new OrdinaryUserDiscount(),
                              DeliveryInformation.createEmpty(), OrderStatus.IN_PROGRESS, 0L, purchaser, new ArrayList<Product>())
        orderRepository.database.put(order.getId().value, order)

        when:
        orderService.completeOrderProcess(request)
        then:
        'mock'
        1 * purchaserSupplier.supply(request.purchaserId) >> Optional.of(purchaser)
        'assert'
        def orderFromRepository = orderRepository.database.get("1")
        assert orderFromRepository.orderStatus == OrderStatus.FINISHED
    }
}
