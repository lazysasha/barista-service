package nl.craftsmen.baristaservice;

import java.math.BigDecimal;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nl.craftsmen.baristaservice.models.PriceModel;

@Path("/")
@RegisterRestClient
public interface PricesClient {

    @GET
    @Path("/prices/{productName}")
    @Retry(maxRetries = 5)
    @Fallback(fallbackMethod = "fallback")
    PriceModel get(@PathParam("productName") String productName);

    default PriceModel fallback(String productName) {
        PriceModel priceModel = new PriceModel();
        priceModel.price = BigDecimal.TEN;
        return priceModel;
    }
}
