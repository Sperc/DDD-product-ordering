package application;

import application.orders.OrderApplicationService;
import application.orders.OrderModuleConfiguration;
import application.orders.dto.*;
import com.google.gson.Gson;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        final OrderApplicationService orderApplicationService = new OrderModuleConfiguration().orderApplicationService();

        Spark.post("/orders", (request, response) -> {
            response.type("application/json");
            final CreateOrderRequest createOrderRequest = new Gson().fromJson(request.body(), CreateOrderRequest.class);
            final CreateOrderResponse order = orderApplicationService.createOrder(createOrderRequest);
            return new Gson().toJson(order);
        });
        Spark.post("/orders/:id/products", (request, response) -> {
            response.type("application/json");
            final AddProductRequest req = new Gson().fromJson(request.body(), AddProductRequest.class);
            orderApplicationService.addProduct(req, request.params(":id"));
            return new Gson().toJson("SUCCESS");
        });

        Spark.post("/orders/:id/deliveryAddress", (request, response) -> {
            response.type("application/json");
            final AddDeliveryAddressRequest createOrderRequest = new Gson().fromJson(request.body(), AddDeliveryAddressRequest.class);
            orderApplicationService.addDeliveryAddress(createOrderRequest, request.params(":id"));
            return new Gson().toJson("SUCCESS");
        });
        Spark.post("/orders/:id/calculateDiscount", (request, response) -> {
            response.type("application/json");
            final CalculateDiscountRequest req = new Gson().fromJson(request.body(), CalculateDiscountRequest.class);
            orderApplicationService.calculate(req, request.params(":id"));
            return new Gson().toJson("SUCCESS");
        });
        Spark.post("/orders/:id/complete", (request, response) -> {
            response.type("application/json");
            final CompleteOrderProcessRequest req = new Gson().fromJson(request.body(), CompleteOrderProcessRequest.class);
            orderApplicationService.completeOrderProcess(req, request.params(":id"));
            return new Gson().toJson("SUCCESS");
        });
    }
}
