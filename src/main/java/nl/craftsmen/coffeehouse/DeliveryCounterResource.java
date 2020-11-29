package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Delivery;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class DeliveryCounterResource {

    @Inject
    @Channel("in-counter")
    Publisher<Delivery> deliveries;

    @GET
    @Path("/outcounter")
    @SseElementType(MediaType.APPLICATION_JSON)
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<Delivery> stream() {
        return deliveries;
    }
}
