package nl.craftsmen.baristaservice;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
@RegisterRestClient
public interface PricesClient {

    @GET
    @Path("/prices/{productName}")
    PriceModel get(@PathParam("productName") String productName);
}
