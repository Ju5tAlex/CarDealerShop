package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;

import java.util.List;
import java.util.Set;

public interface CarService {

    List<Car> getAllCars();


    Car getCar(int id);

    Car createCar(Car car);

    Car updateCar(Car updatedCar, int id);

    void deleteCar(int id);

    Set<Client> getClients(int id);

    Set<Part> getParts(int carId);

    Set<Client> addClient(int carId, int clientId);

    Set<Part> addPart(int carId, int partId);

    Set<Client> deleteClient(int carId, int clientId);

    Set<Part> deletePart(int carId, int partId);
}
