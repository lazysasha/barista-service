package nl.craftsmen.baristaservice.models;

import javax.validation.constraints.NotEmpty;

public class DeliveryModel {

    @NotEmpty
    public String customerName;

    @NotEmpty
    public Beverage beverage;

    @NotEmpty
    public String createdBy;

}
