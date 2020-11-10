package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.CarRepository;
import org.example.task.cardealershop.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

}
