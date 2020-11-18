package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/parts",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
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

    @GetMapping("/{id}/manufacturer")
    public Manufacturer getPartsManufacturer(@PathVariable int id) {
        return partService.getPartsManufacturer(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Part createPart(@RequestBody Part part, @RequestParam("manufacturer_id") int manufacturerId) {
        return partService.createPart(part, manufacturerId);
    }

    @PostMapping("/mq/{id}")
    public String sendPartToMQ(@PathVariable int id) {
        return partService.sendPartToMQ(id);
    }

    @DeleteMapping("/mq/{id}")
    public String sendPartToMQAndDelete(@PathVariable int id) {
        return partService.sendPartToMQAndDelete(id);
    }

    @GetMapping("/mq")
    public Part getPartFromMQ() {
        return partService.getPartFromMQ();
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Part updatePart(@RequestBody Part part, @PathVariable int id, @RequestParam("manufacturer_id") int manufacturerId) {
        return partService.updatePart(part, id, manufacturerId);
    }

    @DeleteMapping("/{id}")
    public void deletePart(@PathVariable int id) {
        partService.deletePart(id);
    }
}
