package nl.craftsmen.coffeehouse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

import nl.craftsmen.coffeehouse.models.Delivery;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
@ActivateRequestContext
public class Barista {

    @ConfigProperty(name = "barista.name")
    String baristaName;

    Logger logger = LoggerFactory.getLogger(Barista.class);

    @Incoming("from-orders")
    @Blocking
    @Outgoing("out-counter")
    public Delivery makeBeverage(Long orderId) {
        logger.info("retrieving order with id: {}", orderId);
        OrderEntity orderEntity = OrderEntity.findById(orderId);
        Delivery delivery = new Delivery();
        delivery.createdBy = baristaName;
        delivery.customerName = orderEntity.customerName;
        delivery.beverage = orderEntity.beverage;
        logger.info("send delivery to counter: {}", delivery);
        return delivery;
    }
}
