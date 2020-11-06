package nl.craftsmen.baristaservice;

import io.smallrye.reactive.messaging.annotations.Blocking;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

import nl.craftsmen.baristaservice.models.Delivery;

@ApplicationScoped
@ActivateRequestContext
public class Barista {

    @ConfigProperty(name = "barista.name")
    String baristaName;

    Logger logger = LoggerFactory.getLogger(Barista.class);

    @Inject
    @Channel("out-counter")
    Emitter<Delivery> deliveryEmitter;

    @Incoming("from-orders")
    @Blocking
    public void makeBeverage(Long orderId) {
        logger.info("retrieving order");
        OrderEntity orderEntity = OrderEntity.findById(orderId);
        logger.info("order retrieved");
        Delivery delivery = new Delivery();
        delivery.createdBy = baristaName;
        delivery.customerName = orderEntity.name;
        delivery.beverage = orderEntity.product;
        logger.info("send to counter");
        deliveryEmitter.send(delivery);
    }
}
