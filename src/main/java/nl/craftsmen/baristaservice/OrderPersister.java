package nl.craftsmen.baristaservice;

import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Merge;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

@ApplicationScoped
@ActivateRequestContext
public class OrderPersister {

    Logger logger = LoggerFactory.getLogger(OrderPersister.class);

    @Incoming("incoming-orders")
    @Merge
    @Blocking
    @Transactional
    @Outgoing("out-order-retrieve")
    public String incoming(OrderModel orderModel) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.name = orderModel.name;
        orderEntity.persist();
        logger.info("persisted order");
        return "retrieve";
    }

}
