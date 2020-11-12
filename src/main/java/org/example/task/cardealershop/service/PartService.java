package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Part;

import java.util.List;

public interface PartService {

    List<Part> getAllParts();

    Part getPart(int id);

    Part createPart(Part part);

    Part updatePart(Part updatedPart, int id);

    void deletePart(int id);
}
