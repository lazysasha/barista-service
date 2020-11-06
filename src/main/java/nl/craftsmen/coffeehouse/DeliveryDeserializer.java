package nl.craftsmen.coffeehouse;

import nl.craftsmen.coffeehouse.models.Delivery;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class DeliveryDeserializer extends ObjectMapperDeserializer<Delivery> {

    public DeliveryDeserializer(){
        super(Delivery.class);
    }
}

