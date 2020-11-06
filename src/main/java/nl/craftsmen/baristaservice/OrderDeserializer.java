package nl.craftsmen.baristaservice;

import nl.craftsmen.baristaservice.models.Order;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<Order> {

    public OrderDeserializer(){
        super(Order.class);
    }
}

