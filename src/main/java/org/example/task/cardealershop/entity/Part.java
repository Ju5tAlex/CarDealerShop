package org.example.task.cardealershop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 45, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "code", nullable = false, unique = true)
    private int code;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @JsonIgnore
    @ManyToMany(mappedBy = "partList")
    private List<Car> carList;

    public Part() {
    }

    public Part(String name, String description, int code, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.manufacturer = manufacturer;
    }

    public Part(int id, String name, String description, int code, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.manufacturer = manufacturer;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code=" + code +
                ", manufacturer=" + manufacturer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return id == part.id &&
                code == part.code &&
                Objects.equals(name, part.name) &&
                Objects.equals(description, part.description) &&
                Objects.equals(manufacturer, part.manufacturer) &&
                Objects.equals(carList, part.carList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, code, manufacturer, carList);
    }
}
