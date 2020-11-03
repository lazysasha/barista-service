package nl.craftsmen.baristaservice.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderModel {

    @NotEmpty
    public String name;

    @NotNull
    public ProductType product;

}
