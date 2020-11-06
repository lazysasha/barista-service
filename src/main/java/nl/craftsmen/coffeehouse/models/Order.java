package nl.craftsmen.coffeehouse.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Order {

    @NotEmpty
    public String customerName;

    @NotNull
    public Beverage beverage;

}
