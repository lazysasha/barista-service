package nl.craftsmen.coffeehouse;

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

import nl.craftsmen.coffeehouse.models.Menu;
import nl.craftsmen.coffeehouse.models.Order;
import nl.craftsmen.coffeehouse.models.Product;
import nl.craftsmen.coffeehouse.models.Beverage;

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
    Emitter<Order> orderEmitter;

    @GET
    @Path("/hello")
    public String echo() {
        return "hello!";
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Menu menu() {
        Menu menu = new Menu();
        menu.greetingMessage = message;
        for (Beverage beverage : Beverage.values()) {
            Product product = new Product();
            product.beverage = beverage;
            product.price = pricesClient.get(beverage.toString()).price;
            menu.menu.add(product);
        }
        return menu;
    }

    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "receivedOrders", description = "How many orders are received")
    @Timed(name = "orderTimer", description = "A measure of how long it takes to process an order")
    public void order(@Valid Order order) {
        logger.info("sending ordermodel");
        orderEmitter.send(order);
    }
}
