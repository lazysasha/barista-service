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

@ApplicationScoped
@ActivateRequestContext
public class Barista {
    private static final Logger logger = LoggerFactory.getLogger(Barista.class);

    @ConfigProperty(name = "barista.name")
    String baristaName;

    @Blocking
    @Incoming("from-orders")
    @Outgoing("out-counter")
    public Delivery makeBeverage(Long orderId) {
        logger.info("Received oderId {} for barista {}", orderId, baristaName);
        OrderEntity orderEntity = OrderEntity.findById(orderId);
        Delivery delivery = new Delivery();
        delivery.createdBy = baristaName;
        delivery.customerName = orderEntity.customerName;
        delivery.beverage = orderEntity.beverage;
        logger.info("send delivery to counter: {}", delivery);
        return delivery;
    }
}
