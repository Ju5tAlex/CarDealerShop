package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.CarRepository;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.DuplicatedListEntityException;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private ClientService clientService;
    private PartService partService;
    private Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ClientService clientService, PartService partService) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.partService = partService;
    }

    @Override
    public List<Car> getAllCars() {
        logger.info("Getting list of all cars from database");
        return carRepository.findAll();
    }

    @Override
    public Car getCar(int carId) {
        logger.info(String.format("Getting a car with id=%d from database", carId));
        return carRepository.findById(carId).orElseThrow(() -> new EntityByIdNotFoundException("Car", carId));
    }

    @Override
    public Car createCar(Car car) {
        logger.info("Creating a new car");
        car.setId(0);
        car.setClientList(getUpdatedClientList(car));
        return carRepository.save(car);
    }

    private Set<Client> getUpdatedClientList(Car car) {
        Set<Client> clients = new HashSet<>();
        car.getClientList().forEach((client -> clients.add(clientService.getClient(client.getId()))));
        return clients;
    }


    @Override
    public Car updateCar(Car updatedCar, int carId) {
        logger.info(String.format("Updating info for a car with id=%d", carId));
        return carRepository.findById(carId)
                .map((car) -> {
                    car.setModelName(updatedCar.getModelName());
                    car.setPrice(updatedCar.getPrice());
                    car.setEnginePower(updatedCar.getEnginePower());
                    car.setEnginePower(updatedCar.getEnginePower());
                    car.setColour(updatedCar.getColour());
                    car.setClientList(getUpdatedClientList(updatedCar));
                    return carRepository.save(car);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Car", carId));
    }

    @Override
    public void deleteCar(int carId) {
        logger.info(String.format("Deleting a car with id=%d", carId));
        if (!carRepository.existsById(carId)) throw new EntityByIdNotFoundException("Car", carId);
        carRepository.deleteById(carId);
    }

    @Override
    public Set<Client> getClients(int carId) {
        logger.info(String.format("Getting a list of clients for a car with id=%d", carId));
        return getCar(carId).getClientList();
    }

    @Override
    public Set<Client> addClient(int carId, int clientId) {
        logger.info(String.format("Adding client to a car with id=%d", carId));
        Car car = getCar(carId);
        Set<Client> clientList = car.getClientList();
        Client client = clientService.getClient(clientId);
        if (clientList.contains(client)) throw new DuplicatedListEntityException("Client", clientId);
        else clientList.add(client);
        carRepository.save(car);
        return clientList;
    }

    @Override
    public Set<Client> deleteClient(int carId, int clientId) {
        logger.info(String.format("Removing client from a car with id=%d", carId));
        Car car = getCar(carId);
        Set<Client> clientList = car.getClientList();
        Client client = clientService.getClient(clientId);
        if (!clientList.contains(client)) throw new EntityByIdNotFoundException("Client", clientId);
        else clientList.remove(client);
        carRepository.save(car);
        return clientList;
    }

    @Override
    public Set<Part> getParts(int carId) {
        logger.info(String.format("Getting a list of parts for a car with id=%d", carId));
        return getCar(carId).getPartList();
    }

    @Override
    public Set<Part> addPart(int carId, int partId) {
        logger.info(String.format("Adding part to a car with id=%d", carId));
        Car car = getCar(carId);
        Set<Part> partList = car.getPartList();
        Part part = partService.getPart(partId);
        if (partList.contains(part)) throw new DuplicatedListEntityException("Part", partId);
        else partList.add(part);
        carRepository.save(car);
        return partList;
    }

    @Override
    public Set<Part> deletePart(int carId, int partId) {
        logger.info(String.format("Removing part from a car with id=%d", carId));
        Car car = getCar(carId);
        Set<Part> partList = car.getPartList();
        Part part = partService.getPart(partId);
        if (!partList.contains(part)) throw new EntityByIdNotFoundException("Part", partId);
        else partList.remove(part);
        carRepository.save(car);
        return partList;
    }
}
