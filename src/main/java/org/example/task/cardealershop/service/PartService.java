package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;

import java.util.List;

public interface PartService {

    List<Part> getAllParts();

    Part getPart(int id);

    Manufacturer getPartsManufacturer(int id);

    Part createPart(Part part);

    Part updatePart(Part updatedPart, int id);

    void deletePart(int id);

    String sendPartToMQ(int id);

    String sendPartToMQAndDelete(int id);

    Part getPartFromMQ();
}
