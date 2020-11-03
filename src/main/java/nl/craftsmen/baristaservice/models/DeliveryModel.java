package nl.craftsmen.baristaservice.models;

import javax.validation.constraints.NotEmpty;

public class DeliveryModel {

    @NotEmpty
    public String name;

    @NotEmpty
    public ProductType product;


    @NotEmpty
    public String createdBy;

}
