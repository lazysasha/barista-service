package nl.craftsmen.baristaservice;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderModelDeserializer extends ObjectMapperDeserializer<OrderModel> {

    public OrderModelDeserializer(){
        super(OrderModel.class);
    }
}

