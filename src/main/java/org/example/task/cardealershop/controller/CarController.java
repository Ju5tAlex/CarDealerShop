package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.dto.CarDTO;
import org.example.task.cardealershop.dto.ClientDTO;
import org.example.task.cardealershop.dto.PartDTO;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/cars",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class CarController {

    private CarService carService;
    private ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<CarDTO> getAllCars() {
        return modelMapper.map(carService.getAllCars(), new TypeToken<Set<CarDTO>>(){}.getType());
    }

    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable int carId) {
        return modelMapper.map(carService.getCar(carId), CarDTO.class);
    }

    @GetMapping("/{id}/entity")
    public Car getCarEntity(@PathVariable int id) {
        return carService.getCar(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        Car car = modelMapper.map(carDTO, Car.class);
        return modelMapper.map(carService.createCar(car), CarDTO.class);
    }

    @PutMapping(value = "/{carId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CarDTO createCar(@RequestBody CarDTO carDTO, @PathVariable int carId) {
        Car car = modelMapper.map(carDTO, Car.class);
        return modelMapper.map(carService.updateCar(car, carId), CarDTO.class);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable int carId) {
        carService.deleteCar(carId);
    }

    @GetMapping("/{carId}/clients")
    public Set<ClientDTO> getClients(@PathVariable int carId) {
        Set<Client> clients = carService.getClients(carId);
        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toSet());
    }

    @PutMapping("/{carId}/clients/{clientId}")
    public Set<ClientDTO> addClientToCar(@PathVariable int carId, @PathVariable int clientId) {
        Set<Client> clients = carService.addClient(carId, clientId);
        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toSet());
    }

    @DeleteMapping("/{carId}/clients/{clientId}")
    public Set<ClientDTO> deleteClientFromCar(@PathVariable int carId, @PathVariable int clientId) {
        Set<Client> clients = carService.deleteClient(carId, clientId);
        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toSet());
    }

    @GetMapping("/{carId}/parts")
    public Set<PartDTO> getParts(@PathVariable int carId) {
        Set<Part> parts = carService.getParts(carId);
        return parts.stream().map(part -> modelMapper.map(part, PartDTO.class)).collect(Collectors.toSet());
    }

    @PutMapping("/{carId}/parts/{partId}")
    public Set<PartDTO> addPartToCar(@PathVariable int carId, @PathVariable int partId) {
        Set<Part> parts = carService.addPart(carId, partId);
        return parts.stream().map(part -> modelMapper.map(part, PartDTO.class)).collect(Collectors.toSet());
    }

    @DeleteMapping("/{carId}/parts/{partId}")
    public Set<PartDTO> deletePartFromCar(@PathVariable int carId, @PathVariable int partId) {
        Set<Part> parts = carService.deletePart(carId, partId);
        return parts.stream().map(part -> modelMapper.map(part, PartDTO.class)).collect(Collectors.toSet());
    }
}
