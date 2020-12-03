package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Beverage;
import nl.craftsmen.coffeehouse.models.Menu;
import nl.craftsmen.coffeehouse.models.Product;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@Path("/")
public class OrderCounterResource {
    @ConfigProperty(name = "greeting.message")
    String welcomeMessage;

    @Inject
    @RestClient
    PricesClient pricesClient;


    @GET
    @Path("/menu")
    @Produces("application/json")
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
}
