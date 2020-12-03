package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Beverage;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@Readiness
public class PriceClientHealthCheck implements HealthCheck {
    @Inject
    @RestClient
    PricesClient client;

    @Override
    public HealthCheckResponse call() {
        final HealthCheckResponseBuilder builder = HealthCheckResponse.named("prices client health check");
        try {
            client.get(Beverage.latte.toString());
            builder.up();
        } catch (Exception e) {
            builder.down();
        }
        return builder.build();
    }
}
