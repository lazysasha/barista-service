package nl.craftsmen.coffeehouse;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import nl.craftsmen.coffeehouse.models.Beverage;

import javax.persistence.Entity;

/**
 * @author Oleksandr Shynkariuk sasha@routigo.com on 03/12/2020.
 */
@Entity
public class OrderEntity extends PanacheEntity {
    public String customerName;
    public Beverage beverage;
}
