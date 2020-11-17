package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ManufacturerRepository;
import org.example.task.cardealershop.dao.PartRepository;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
class PartServiceImplTest {

    @MockBean
    private PartRepository partRepository;

    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private PartService partService;

    private Manufacturer manufacturer = new Manufacturer(12, "BMZ", "Germany", "Munich", 1234456);
    private Part part1 = new Part(31, "Engine", "Powerful engine", 1568, manufacturer);
    private Part part2 = new Part(32, "Engine", "Very powerful engine", 34354, manufacturer);
    private Part part3 = new Part(33, "Engine", "Incredibly powerful engine", 5444, manufacturer);
    private List<Part> parts = new ArrayList<>();


    @BeforeEach
    public void beforeTest() {
        parts.add(part1);
        parts.add(part2);
        parts.add(part3);
    }

    @AfterEach
    public void afterTest() {
        parts.clear();
    }

    @Test
    void getAllParts_ListContainsAllParts_SizesAreEqual() {
        Mockito.when(partRepository.findAll()).thenReturn(parts);
        Assert.assertEquals(parts.size(), partService.getAllParts().size());
    }

    @Test
    void getPart_WithExistingId_PartFound() {
        int partId = part2.getId();
        Mockito.when(partRepository.findById(partId)).thenReturn(Optional.of(part2));
        Assert.assertEquals(part2, partService.getPart(partId));
    }

    @Test
    void getPart_WithNonExistingId_ExceptionThrown() {
        int partId = 69;
        Mockito.when(partRepository.findById(partId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> partService.getPart(partId));
    }

    @Test
    void getPartsManufacturer_WithExistingId_ManufacturerFound() {
        int partId = part1.getId();
        Mockito.when(partRepository.findById(partId)).thenReturn(Optional.of(part1));
        assertEquals(manufacturer, partService.getPartsManufacturer(partId));
    }

    @Test
    void getPartsManufacturer_WithNonExistingId_ExceptionThrown() {
        int partId = 34;
        Mockito.when(partRepository.findById(partId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> partService.getPartsManufacturer(partId));
    }

    @Test
    void createPart() {
    }

    @Test
    void updatePart() {
    }

    @Test
    void deletePart() {
    }
}