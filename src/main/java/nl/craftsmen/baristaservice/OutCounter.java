package nl.craftsmen.baristaservice;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/outcounter")
public class OutCounter {

    Logger logger = LoggerFactory.getLogger(OutCounter.class);

    @Inject
    @Channel("in-counter")
    Publisher<CompleteOrder> prices;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("application/json")
    public Publisher<CompleteOrder> stream() {
        return prices;
    }
}
