package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ManufacturerRepository;
import org.example.task.cardealershop.dao.PartRepository;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private PartRepository partRepository;
    private ManufacturerRepository manufacturerRepository;
    private Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ManufacturerRepository manufacturerRepository) {
        this.partRepository = partRepository;
        this.manufacturerRepository = manufacturerRepository;
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
}
