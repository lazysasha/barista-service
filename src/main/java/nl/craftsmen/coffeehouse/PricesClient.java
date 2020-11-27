package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Price;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient
public interface PricesClient {

    @GET
    @Path("/prices/{productName}")
    Price get(@PathParam("productName") String productName);
}
