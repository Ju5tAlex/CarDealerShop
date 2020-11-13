package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/cars",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping("/{carId}")
    public Car getCar(@PathVariable int carId) {
        return carService.getCar(carId);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
    }

    @PutMapping(value = "/{carId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Car createCar(@RequestBody Car car, @PathVariable int carId) {
        return carService.updateCar(car, carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable int carId) {
        carService.deleteCar(carId);
    }

    @GetMapping("/{carId}/clients")
    public List<Client> getClients(@PathVariable int carId) {
        return carService.getClients(carId);
    }

    @PutMapping("/{carId}/clients/{clientId}")
    public List<Client> addClientToCar(@PathVariable int carId, @PathVariable int clientId) {
        return carService.addClient(carId, clientId);
    }

    @DeleteMapping("/{carId}/clients/{clientId}")
    public List<Client> deleteClientFromCar(@PathVariable int carId, @PathVariable int clientId) {
        return carService.deleteClient(carId, clientId);
    }

    @GetMapping("/{carId}/parts")
    public List<Part> getParts(@PathVariable int carId) {
        return carService.getParts(carId);
    }

    @PutMapping("/{carId}/parts/{partId}")
    public List<Part> addPartToCar(@PathVariable int carId, @PathVariable int partId) {
        return carService.addPart(carId, partId);
    }

    @DeleteMapping("/{carId}/parts/{partId}")
    public List<Part> deletePartFromCar(@PathVariable int carId, @PathVariable int partId) {
        return carService.deletePart(carId, partId);
    }

}
