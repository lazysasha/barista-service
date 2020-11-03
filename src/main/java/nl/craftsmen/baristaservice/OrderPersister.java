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

import nl.craftsmen.baristaservice.models.OrderModel;

@ApplicationScoped
@ActivateRequestContext
public class OrderPersister {

    Logger logger = LoggerFactory.getLogger(OrderPersister.class);

    @Incoming("incoming-orders")
    @Merge
    @Blocking
    @Transactional
    @Outgoing("to-barista")
    public Long incoming(OrderModel orderModel) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.name = orderModel.name;
        orderEntity.product = orderModel.product;
        orderEntity.persist();
        logger.info("persisted order");
        return orderEntity.id;
    }

}
