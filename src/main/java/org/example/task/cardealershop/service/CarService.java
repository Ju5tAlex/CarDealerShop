package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();


    Car getCar(int id);

    Car createCar(Car car);

    Car updateCar(Car updatedCar, int id);

    void deleteCar(int id);
}
