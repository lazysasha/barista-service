package nl.craftsmen.baristaservice;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

import nl.craftsmen.baristaservice.models.Beverage;

@Entity
public class OrderEntity extends PanacheEntity {

    public String name;

    public Beverage product;
}
