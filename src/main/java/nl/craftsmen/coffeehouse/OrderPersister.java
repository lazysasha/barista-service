package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Order;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

@ApplicationScoped
@ActivateRequestContext
public class OrderPersister {

    Logger logger = LoggerFactory.getLogger(OrderPersister.class);

    @Incoming("incoming-orders")
    public void incoming(Order order) {
        logger.info("persisted order for Customer '{}' with Beverage '{}'", order.customerName, order.beverage);
    }
}
