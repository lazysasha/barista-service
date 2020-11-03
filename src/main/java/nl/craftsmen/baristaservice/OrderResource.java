package nl.craftsmen.baristaservice;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/")
public class OrderResource {

    Logger logger = LoggerFactory.getLogger(OrderResource.class);

    @GET
    @Path("/hello")
    public String echo() {
        return "hello!";
    }

    @Inject
    @Channel("outgoing-orders")
    Emitter<OrderModel> orderEmitter;

    @POST
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public void order(@Valid OrderModel orderModel) {
        logger.info("sending ordermodel");
        orderEmitter.send(orderModel);
    }
}
