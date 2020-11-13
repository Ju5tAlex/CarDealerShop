package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;

import java.util.List;

public interface PartService {

    List<Part> getAllParts();

    Part getPart(int id);

    Manufacturer getPartsManufacturer(int id);

    Part createPart(Part part, int manufacturerId);

    Part updatePart(Part updatedPart, int id, int manufacturerId);

    void deletePart(int id);
}
