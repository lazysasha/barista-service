package nl.craftsmen.baristaservice;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SnackProvider {

    @Inject
    @RestClient
    PricesClient pricesClient;

    Logger logger = LoggerFactory.getLogger(SnackProvider.class);

    @Incoming("in-snack-provider")
    @Outgoing("out-counter")
    public CompleteOrder retrieve(String retrieve) {
        logger.info("snack provider");
        CompleteOrder completeOrder = new CompleteOrder();
        completeOrder.forWho = "michel";
        completeOrder.name = "expresso";
        PriceModel priceModel = pricesClient.get("f");
        completeOrder.snack = "cookies!";
        completeOrder.price = priceModel.price;
        return completeOrder;
    }
}
