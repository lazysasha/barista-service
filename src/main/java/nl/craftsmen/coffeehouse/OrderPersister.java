package nl.craftsmen.coffeehouse;

import io.smallrye.reactive.messaging.annotations.Blocking;
import nl.craftsmen.coffeehouse.models.Order;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

@ApplicationScoped
@ActivateRequestContext
public class OrderPersister {

    Logger logger = LoggerFactory.getLogger(OrderPersister.class);

    @Blocking
    @Transactional
    @Incoming("incoming-orders")
    public void incoming(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.customerName = order.customerName;
        orderEntity.beverage = order.beverage;
        orderEntity.persist();
        logger.info("persisted order for Customer '{}' with Beverage '{}'", order.customerName, order.beverage);
    }
}
