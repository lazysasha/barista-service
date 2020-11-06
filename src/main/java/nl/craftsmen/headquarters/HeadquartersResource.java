package nl.craftsmen.headquarters;

import nl.craftsmen.baristaservice.models.Price;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Path("/")
public class HeadquartersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/prices/{productName}")
    public Price getPrice(@PathParam("productName") String productName) {
        Price price = new Price();
        price.price = generateRandomBigDecimalFromRange(BigDecimal.ONE, BigDecimal.TEN);
        return price;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/prices/{productName}/v2")
    public Price getPriceV2(@PathParam("productName") String productName) {
        throw new RuntimeException("this bloody thing never works!");
    }

    private static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
    }
}
