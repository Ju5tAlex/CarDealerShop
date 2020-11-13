package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();


    Car getCar(int id);

    Car createCar(Car car);

    Car updateCar(Car updatedCar, int id);

    void deleteCar(int id);

    List<Client> getClients(int id);

    List<Part> getParts(int carId);

    List<Client> addClient(int carId, int clientId);

    List<Part> addPart(int carId, int partId);

    List<Client> deleteClient(int carId, int clientId);

    List<Part> deletePart(int carId, int partId);
}
