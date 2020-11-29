package nl.craftsmen.coffeehouse;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import nl.craftsmen.coffeehouse.models.Delivery;

public class DeliveryDeserializer extends ObjectMapperDeserializer<Delivery> {

    public DeliveryDeserializer() {
        super(Delivery.class);
    }
}

