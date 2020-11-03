package nl.craftsmen.baristaservice;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.craftsmen.baristaservice.models.MenuModel;
import nl.craftsmen.baristaservice.models.OrderModel;
import nl.craftsmen.baristaservice.models.Product;
import nl.craftsmen.baristaservice.models.ProductType;

@Path("/")
public class OrderCounterResource {

    Logger logger = LoggerFactory.getLogger(OrderCounterResource.class);

    @ConfigProperty(name = "greeting.message")
    String message;

    @Inject
    @RestClient
    PricesClient pricesClient;

    @Inject
    @Channel("outgoing-orders")
    Emitter<OrderModel> orderEmitter;

    @GET
    @Path("/hello")
    public String echo() {
        return "hello!";
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public MenuModel menu() {
        MenuModel menuModel = new MenuModel();
        menuModel.greetingMessage = message;
        for (ProductType productType: ProductType.values()) {
            Product product = new Product();
            product.name = productType;
            product.price = pricesClient.get(productType.toString()).price;
            menuModel.menu.add(product);
        }
        return menuModel;
    }

    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "receivedOrders", description = "How many orders are received")
    @Timed(name = "orderTimer", description = "A measure of how long it takes to process an order")
    public void order(@Valid OrderModel orderModel) {
        logger.info("sending ordermodel");
        orderEmitter.send(orderModel);
    }
}
