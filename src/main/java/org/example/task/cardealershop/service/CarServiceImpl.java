package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.CarRepository;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.DuplicatedListEntityException;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private ClientService clientService;
    private PartService partService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ClientService clientService, PartService partService) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.partService = partService;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCar(int carId) {
        return carRepository.findById(carId).orElseThrow(() -> new EntityByIdNotFoundException("Car", carId));
    }

    @Override
    public Car createCar(Car car) {
        car.setId(0);
        car.setClientList(getUpdatedClientList(car));
        return carRepository.save(car);
    }

    private List<Client> getUpdatedClientList(Car car) {
        List<Client> clients = new ArrayList<>();
        car.getClientList().forEach((client -> clients.add(clientService.getClient(client.getId()))));
        return clients;
    }


    @Override
    public Car updateCar(Car updatedCar, int carId) {
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
        carRepository.deleteById(carId);
    }

    @Override
    public List<Client> getClients(int carId) {
        return getCar(carId).getClientList();
    }

    @Override
    public List<Client> addClient(int carId, int clientId) {
        Car car = getCar(carId);
        List<Client> clientList = car.getClientList();
        Client client = clientService.getClient(clientId);
        if (clientList.contains(client)) throw new DuplicatedListEntityException("Client", clientId);
        else clientList.add(client);
        carRepository.save(car);
        return clientList;
    }

    @Override
    public List<Client> deleteClient(int carId, int clientId) {
        Car car = getCar(carId);
        List<Client> clientList = car.getClientList();
        Client client = clientService.getClient(clientId);
        if (!clientList.contains(client)) throw new EntityByIdNotFoundException("Client", clientId);
        else clientList.remove(client);
        carRepository.save(car);
        return clientList;
    }

    @Override
    public List<Part> getParts(int carId) {
        return getCar(carId).getPartList();
    }

    @Override
    public List<Part> addPart(int carId, int partId) {
        Car car = getCar(carId);
        List<Part> partList = car.getPartList();
        Part part = partService.getPart(partId);
        if (partList.contains(part)) throw new DuplicatedListEntityException("Part", partId);
        else partList.add(part);
        carRepository.save(car);
        return partList;
    }

    @Override
    public List<Part> deletePart(int carId, int partId) {
        Car car = getCar(carId);
        List<Part> partList = car.getPartList();
        Part part = partService.getPart(partId);
        if (!partList.contains(part)) throw new EntityByIdNotFoundException("Part", partId);
        else partList.remove(part);
        carRepository.save(car);
        return partList;
    }
}
