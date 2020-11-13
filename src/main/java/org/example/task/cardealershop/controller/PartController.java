package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.service.CarService;
import org.example.task.cardealershop.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {

    private PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{id}")
    public Part getPart(@PathVariable int id) {
        return partService.getPart(id);
    }

    @PostMapping()
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }

    @PutMapping("/{id}")
    public Part createPart(@RequestBody Part part, @PathVariable int id) {
        return partService.updatePart(part, id);
    }

    @DeleteMapping("/{id}")
    public void deletePart(@PathVariable int id) {
        partService.deletePart(id);
    }
}
