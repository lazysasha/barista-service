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

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */

@Path("/")
public class DeliveryCounterResource {

    @Inject
    @Channel("in-counter")
    Publisher<Delivery> deliveryPublisher;

    @GET
    @Path("/outcounter")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<Delivery> stream() {
        return deliveryPublisher;
    }
}
