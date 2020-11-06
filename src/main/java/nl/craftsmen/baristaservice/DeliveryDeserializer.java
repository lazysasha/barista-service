package nl.craftsmen.baristaservice;

import nl.craftsmen.baristaservice.models.Delivery;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class DeliveryDeserializer extends ObjectMapperDeserializer<Delivery> {

    public DeliveryDeserializer(){
        super(Delivery.class);
    }
}

