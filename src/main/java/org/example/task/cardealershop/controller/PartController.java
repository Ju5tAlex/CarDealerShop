package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.dto.PartDTO;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.service.PartService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(
        value = "/parts",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class PartController {

    private PartService partService;
    private ModelMapper modelMapper;

    @Autowired
    public PartController(PartService partService, ModelMapper modelMapper) {
        this.partService = partService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Set<PartDTO> getAllParts() {
        return modelMapper.map(partService.getAllParts(), new TypeToken<Set<PartDTO>>(){}.getType());
    }

    @GetMapping("/{id}")
    public PartDTO getPart(@PathVariable int id) {
        return modelMapper.map(partService.getPart(id), PartDTO.class);
    }

    @GetMapping("/{id}/entity")
    public Part getPartEntity(@PathVariable int id) {
        return partService.getPart(id);
    }

    @GetMapping("/{id}/manufacturer")
    public Manufacturer getPartsManufacturer(@PathVariable int id) {
        return partService.getPartsManufacturer(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public PartDTO createPart(@RequestBody PartDTO partDTO) {
        Part part = modelMapper.map(partDTO, Part.class);
        return modelMapper.map(partService.createPart(part), PartDTO.class);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public PartDTO updatePart(@RequestBody PartDTO partDTO, @PathVariable int id) {
        Part part = modelMapper.map(partDTO, Part.class);
        return modelMapper.map(partService.updatePart(part, id), PartDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deletePart(@PathVariable int id) {
        partService.deletePart(id);
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
}
