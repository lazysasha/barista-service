package nl.craftsmen.headquarters;

import nl.craftsmen.coffeehouse.models.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

@Path("/")
public class HeadquartersResource {

    Logger logger = LoggerFactory.getLogger(HeadquartersResource.class);

    private final AtomicLong counter = new AtomicLong(0);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/prices/{productName}")
    public Price getPrice(@PathParam("productName") String productName) {
        logger.info("Retrieve the price of the product");
        Price price = new Price();
        price.price = generateRandomBigDecimalFromRange(BigDecimal.ONE, BigDecimal.TEN);
        return price;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/prices/{productName}/v2")
    public Price getPriceV2(@PathParam("productName") String productName) throws InterruptedException {
        final long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 2 > 0) {
            logger.info("It takes a while ... ");
            Thread.sleep(500);
            Price price = new Price();
            price.price = generateRandomBigDecimalFromRange(BigDecimal.ONE, BigDecimal.TEN);
            return price;
        } else {
            logger.info("It broke down ...");
            throw new RuntimeException("this bloody thing never works!");
        }
    }

    private static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }
}
