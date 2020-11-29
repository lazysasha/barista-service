package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Beverage;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;

@Readiness
public class PriceClientHealthCheck implements HealthCheck {

    @Inject
    @RestClient
    PricesClient pricesClient;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Prices client health check");
        try {
            pricesClient.get(Beverage.latte.toString());
            responseBuilder.up();
        } catch (Exception e) {
            responseBuilder.down();
        }
        return responseBuilder.build();
    }
}
