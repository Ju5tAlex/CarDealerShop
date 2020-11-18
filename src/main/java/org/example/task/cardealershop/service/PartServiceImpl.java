package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ManufacturerRepository;
import org.example.task.cardealershop.dao.PartRepository;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PartServiceImpl implements PartService {

    private PartRepository partRepository;
    private ManufacturerRepository manufacturerRepository;
    private CarService carService;
    private MQService mqService;
    private Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ManufacturerRepository manufacturerRepository,
                           @Lazy CarService carService, MQService mqService) {
        this.partRepository = partRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.mqService = mqService;
        this.carService = carService;
    }

    @Override
    public List<Part> getAllParts() {
        logger.info("Getting list of all parts from database");
        return partRepository.findAll();
    }

    @Override
    public Part getPart(int id) {
        logger.info(String.format("Getting a part with id=%d from database", id));
        return partRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException("Part", id));
    }

    @Override
    public Manufacturer getPartsManufacturer(int id) {
        logger.info(String.format("Getting a manufacturer of a part with id=%d from database", id));
        return getPart(id).getManufacturer();
    }

    @Override
    public Part createPart(Part part, int manufacturerId) {
        logger.info(String.format("Creating a new part with manufacturer which id=%d", manufacturerId));
        part.setId(0);
        part.setManufacturer(getManufacturer(manufacturerId));
        return partRepository.save(part);
    }

    private Manufacturer getManufacturer(int manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityByIdNotFoundException("Manufacturer", manufacturerId));
    }

    @Override
    public Part updatePart(Part updatedPart, int id, int manufacturerId) {
        logger.info(String.format("Updating info for a part with id=%d", id));
        return partRepository.findById(id)
                .map((part) -> {
                    part.setName(updatedPart.getName());
                    part.setDescription(updatedPart.getDescription());
                    part.setCode(updatedPart.getCode());
                    part.setManufacturer(getManufacturer(manufacturerId));
                    return partRepository.save(part);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Part", id));
    }

    @Override
    public void deletePart(int id) {
        logger.info(String.format("Deleting a part with id=%d", id));
        if (!partRepository.existsById(id)) throw new EntityByIdNotFoundException("Part", id);
        partRepository.deleteById(id);
    }

    @Override
    public String sendPartToMQ(int id) {
        return mqService.sendPartToMQ(getPart(id));
    }

    @Override
    public String sendPartToMQAndDelete(int id) {
        String message = mqService.sendPartToMQ(getPart(id));
        deletePart(id);
        message += " and was deleted";
        return message;
    }

    @Override
    public Part getPartFromMQ() {
        Part part = (mqService.getPartFromMQ());
        Set<Car> cars = new HashSet<>();
        part.getCarList().forEach(car -> cars.add(carService.getCar(car.getId())));
        part.setCarList(cars);
        cars.forEach(car -> car.getPartList().add(part));
        int partId = part.getId();
        if (partRepository.existsById(partId)) return getPart(partId);
        else return partRepository.save(part);
    }
}
