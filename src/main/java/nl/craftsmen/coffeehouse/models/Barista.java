package nl.craftsmen.coffeehouse.models;

import io.smallrye.reactive.messaging.annotations.Blocking;
import nl.craftsmen.coffeehouse.OrderEntity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@ApplicationScoped
@ActivateRequestContext
public class Barista {
    private static final Logger logger = LoggerFactory.getLogger(Barista.class);

    @ConfigProperty(name = "barista.name")
    String name;

    @Incoming("from-orders")
    @Blocking
    @Outgoing("out-counter")
    public Delivery makeBeverage(Long orderId) {
        logger.info("Received order with id=" + orderId + " for barista " + name);
        final OrderEntity order = OrderEntity.findById(orderId);
        final Delivery delivery = new Delivery();
        delivery.beverage = order.beverage;
        delivery.customerName = order.customerName;
        delivery.createdBy = name;
        return delivery;
    }
}
