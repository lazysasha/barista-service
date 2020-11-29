package nl.craftsmen.coffeehouse.models;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

@ApplicationScoped
@ActivateRequestContext
public class Barista {
    private static final Logger logger = LoggerFactory.getLogger(Barista.class);

    @ConfigProperty(name = "barista.name")
    String baristaName;

    @Incoming("from-orders")
    public void makeBeverage(Long orderId) {
        logger.info("Received oderId {} for barista {}", orderId, baristaName);
    }
}
