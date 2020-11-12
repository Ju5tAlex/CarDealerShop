package org.example.task.cardealershop.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "city", length = 45)
    private String city;

    @Column(name = "index")
    private int index;

    public Manufacturer() {
    }

    public Manufacturer(String name, String country, String city, int index) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", index=" + index +
                '}';
    }
}
