package application.orders;

import application.orders.dto.*;
import application.shared.ApplicationError;
import application.shared.ApplicationException;
import domain.orders.*;
import domain.orders.dto.AddProductCommand;
import domain.orders.dto.CalculateDiscountCommand;
import domain.orders.dto.CreateOrderResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderApplicationService {
    private final OrdersDomainService ordersDomainService;
    private final PurchaserSupplier purchaserSupplier;
    private final ProductSupplier productSupplier;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        final Purchaser purchaser = getPurchaser(request.getPurchaserId());
        final CreateOrderResult order = ordersDomainService.createOrder(request.toCommand(purchaser));
        return new CreateOrderResponse(order.getOrderId());
    }

    public void addProduct(AddProductRequest request) {
        final ProductContext productContext = getProductContext(request.getProductId());
        final Purchaser purchaser = getPurchaser(request.getPurchaserId());
        ordersDomainService.addProduct(new AddProductCommand(OrderId.of(request.getOrderId()), productContext, purchaser));
    }

    public void calculate(CalculateDiscountRequest request) {
        final Purchaser purchaser = getPurchaser(request.getPurchaserId());
        ordersDomainService.calculateDiscount(new CalculateDiscountCommand(new DiscountCode(request.getCode()), purchaser, OrderId.of(request.getOrderId())));
    }

    public void addDeliveryAddress(AddDeliveryAddressRequest request) {
        final Purchaser purchaser = getPurchaser(request.getPurchaserId());
        ordersDomainService.addDeliveryAddress(new AddDeliveryInformationCommand(OrderId.of(request.getOrderId()),
                                                                                 new DeliveryInformation(request.getStreetName(),
                                                                                                         request.getCity(),
                                                                                                         request.getFirstName(),
                                                                                                         request.getLastName()),
                                                                                 purchaser
        ));
    }

    public void completeOrderProcess(CompleteOrderProcessRequest request) {
        final Purchaser purchaser = getPurchaser(request.getPurchaserId());
        ordersDomainService.finish(OrderId.of(request.getOrderId()), purchaser);
    }

    private ProductContext getProductContext(String id) {
        return productSupplier.supply(id)
                              .orElseThrow(() -> new ApplicationException(ApplicationError.PRODUCT_NOT_FOUND,
                                                                          String.format("Product: %s not found",
                                                                                        id)));
    }

    private Purchaser getPurchaser(String purchaserId) {
        return purchaserSupplier.supply(purchaserId)
                                .orElseThrow(() -> new ApplicationException(ApplicationError.PURCHASER_NOT_FOUND,
                                                                            String.format("Purchaser: %s not found",
                                                                                          purchaserId)));
    }
}
