package nl.craftsmen.baristaservice;

import nl.craftsmen.baristaservice.models.DeliveryModel;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class DeliveryModelDeserializer extends ObjectMapperDeserializer<DeliveryModel> {

    public DeliveryModelDeserializer(){
        super(DeliveryModel.class);
    }
}

