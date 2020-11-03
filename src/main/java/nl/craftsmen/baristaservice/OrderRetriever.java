package nl.craftsmen.baristaservice;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@ActivateRequestContext
public class OrderRetriever {

    Logger logger = LoggerFactory.getLogger(OrderRetriever.class);

    @Inject
    @Channel("out-to-snack-provider")
    Emitter<String> orderEmitter;

    @Incoming("in-order-retrieve")
    @Blocking
    public void retrieve(String retrieve) {
        logger.info("retrieving orders");
        List<OrderEntity> entities = OrderEntity.listAll();
        logger.info("orders retrieved");
        entities.forEach(e-> orderEmitter.send("x"));
    }
}
