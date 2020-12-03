package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Beverage;
import nl.craftsmen.coffeehouse.models.Menu;
import nl.craftsmen.coffeehouse.models.Order;
import nl.craftsmen.coffeehouse.models.Product;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@Path("/")
public class OrderCounterResource {
    Logger logger = LoggerFactory.getLogger(OrderCounterResource.class);

    @ConfigProperty(name = "greeting.message")
    String welcomeMessage;

    @Inject
    @RestClient
    PricesClient pricesClient;

    @Inject
    @Channel("outgoing-orders")
    Emitter<Order> orderEmitter;


    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Menu menu() {
        Menu menu = new Menu();
        menu.greetingMessage = welcomeMessage;
        for (Beverage beverage : Beverage.values()) {
            final Product product = new Product();
            product.beverage = beverage;
            product.price = pricesClient.get(beverage.toString()).price;
            menu.menu.add(product);
        }
        return menu;
    }

    @POST
    @Path("/orders")
    @Consumes(MediaType.APPLICATION_JSON)
    public void order(@Valid Order order) {
        logger.info("Sending order for customer " + order.customerName + " and beverage " + order.beverage + " to an 'outgoing-orders' message channel");
        orderEmitter.send(order);
    }
}
