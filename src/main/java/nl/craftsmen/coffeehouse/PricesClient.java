package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Price;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.math.BigDecimal;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */

@RegisterRestClient
public interface PricesClient {

    @GET
    @Path("/prices/{productName}/v2")
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "fallback")
    @CircuitBreaker()
    Price get(@PathParam("productName") String productName);

    default Price fallback(String productName) {
        Price price = new Price();
        price.price = BigDecimal.valueOf(42.0);
        return price;
    }
}
