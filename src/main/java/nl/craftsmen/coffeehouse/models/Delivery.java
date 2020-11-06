package nl.craftsmen.coffeehouse.models;

import javax.validation.constraints.NotEmpty;

public class Delivery {

    @NotEmpty
    public String customerName;

    @NotEmpty
    public Beverage beverage;

    @NotEmpty
    public String createdBy;

}
