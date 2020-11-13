package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ManufacturerRepository;
import org.example.task.cardealershop.dao.PartRepository;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private PartRepository partRepository;
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ManufacturerRepository manufacturerRepository) {
        this.partRepository = partRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @Override
    public Part getPart(int id) {
        return partRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException("Part", id));
    }

    @Override
    public Part createPart(Part part) {
        part.setId(0);
        part.setManufacturer(getManufacturer(part));
        return partRepository.save(part);
    }

    private Manufacturer getManufacturer(Part part) {
        int manufacturerId = part.getManufacturer().getId();
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityByIdNotFoundException("Manufacturer", manufacturerId));
    }

    @Override
    public Part updatePart(Part updatedPart, int id) {
        return partRepository.findById(id)
                .map((part) -> {
                    part.setName(updatedPart.getName());
                    part.setDescription(updatedPart.getDescription());
                    part.setCode(updatedPart.getCode());
                    part.setManufacturer(getManufacturer(updatedPart));
                    return partRepository.save(part);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Part", id));
    }

    @Override
    public void deletePart(int id) {
        partRepository.deleteById(id);
    }
}
