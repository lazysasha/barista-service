package nl.craftsmen.coffeehouse;

import io.smallrye.reactive.messaging.annotations.Blocking;
import nl.craftsmen.coffeehouse.models.Order;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@ApplicationScoped
@ActivateRequestContext
public class OrderPersister {
    Logger logger = LoggerFactory.getLogger(OrderPersister.class);

    @Incoming("incoming-orders")
    @Transactional
    @Blocking
    public void incoming(Order order) {
        logger.info("Received an order for customer " + order.customerName + " with beverage " + order.beverage);
        final OrderEntity entity = new OrderEntity();
        entity.customerName = order.customerName;
        entity.beverage = order.beverage;
        entity.persist();
        logger.info("Persisted order for customer " + order.customerName + " with beverage " + order.beverage);
    }
}
