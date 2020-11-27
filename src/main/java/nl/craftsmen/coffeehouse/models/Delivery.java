package nl.craftsmen.coffeehouse.models;

import javax.validation.constraints.NotEmpty;

public class Delivery {

    @NotEmpty
    public String customerName;

    @NotEmpty
    public Beverage beverage;

    @NotEmpty
    public String createdBy;

    @Override
    public String toString() {
        return "Customer '" + customerName + "', beverage: '" + beverage + "', prepared by '" + createdBy + "'";
    }
}
