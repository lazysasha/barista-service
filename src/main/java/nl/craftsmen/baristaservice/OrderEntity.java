package nl.craftsmen.baristaservice;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class OrderEntity extends PanacheEntity {

    public String name;
}
