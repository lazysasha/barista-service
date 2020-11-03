package nl.craftsmen.baristaservice;

import javax.validation.constraints.NotEmpty;

public class OrderModel {

    @NotEmpty
    public String name;

}
