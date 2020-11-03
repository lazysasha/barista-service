package nl.craftsmen.baristaservice;

import nl.craftsmen.baristaservice.models.OrderModel;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderModelDeserializer extends ObjectMapperDeserializer<OrderModel> {

    public OrderModelDeserializer(){
        super(OrderModel.class);
    }
}

