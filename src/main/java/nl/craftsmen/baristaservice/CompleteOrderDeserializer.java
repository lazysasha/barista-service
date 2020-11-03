package nl.craftsmen.baristaservice;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CompleteOrderDeserializer extends ObjectMapperDeserializer<CompleteOrder> {

    public CompleteOrderDeserializer(){
        super(CompleteOrder.class);
    }
}

