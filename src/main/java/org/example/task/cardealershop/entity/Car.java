package org.example.task.cardealershop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "model_name", length = 45, unique = true)
    private String modelName;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "engine_power", nullable = false)
    private int enginePower;

    @Column(name = "colour", length = 45, nullable = false)
    private String colour;

    @JsonIgnoreProperties("carList")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "clients_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<Client> clientList;

    @JsonIgnoreProperties("carList")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id"))
    private Set<Part> partList;

    public Car() {
    }

    public Car(String modelName, int price, int enginePower, String colour) {
        this.modelName = modelName;
        this.price = price;
        this.enginePower = enginePower;
        this.colour = colour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Set<Client> getClientList() {
        return clientList;
    }

    public void setClientList(Set<Client> clientList) {
        this.clientList = clientList;
    }

    public Set<Part> getPartList() {
        return partList;
    }

    public void setPartList(Set<Part> partList) {
        this.partList = partList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", price=" + price +
                ", enginePower=" + enginePower +
                ", colour='" + colour + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                price == car.price &&
                enginePower == car.enginePower &&
                Objects.equals(modelName, car.modelName) &&
                Objects.equals(colour, car.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelName, price, enginePower, colour);
    }
}
