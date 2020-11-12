package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable int id) {
        return carService.getCar(id);
    }

    @PostMapping()
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/{id}")
    public Car createCar(@RequestBody Car car, @PathVariable int id) {
        return carService.updateCar(car, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable int id) {
        carService.deleteCar(id);
    }
}
