package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ManufacturerRepository;
import org.example.task.cardealershop.dao.PartRepository;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.entity.Manufacturer;
import org.example.task.cardealershop.entity.Part;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
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

import java.sql.SQLException;
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

    @MockBean
    private MQService mqService;

    @Autowired
    private PartService partService;

    private Manufacturer manufacturer;
    private Part part1;
    private Part part2;
    private Part part2Modified;
    private Part part3;
    private List<Part> parts;
    private Car car;

    @BeforeEach
    public void beforeTest() {
        manufacturer = new Manufacturer(12, "BMZ", "Germany", "Munich", 1234456);
        part1 = new Part(31, "Engine", "Powerful engine", 1568, manufacturer);
        part2 = new Part(32, "Engine", "Very powerful engine", 34354, manufacturer);
        part2Modified = new Part(32, "Engine", "Very powerful engine", 34354, manufacturer);
        part3 = new Part(33, "Engine", "Incredibly powerful engine", 5444, manufacturer);
        car = new Car("Viper", 1234, 453, "Red");
        parts = new ArrayList<>();
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
    void createPart_WithProperData_PartCreated() {
        Part part = new Part(part3.getId(), part3.getName(), part3.getDescription(), part3.getCode(), part3.getManufacturer());
        part.setId(0);
        int manufacturerId = manufacturer.getId();
        Mockito.when(partRepository.save(part)).thenReturn(part3);
        Mockito.when(manufacturerRepository.findById(manufacturerId)).thenReturn(Optional.of(manufacturer));
        assertEquals(part3, partService.createPart(part));
    }

    @Test
    void createPart_WithWrongData_ExceptionThrown() {
        Part part = new Part(part3.getId(), part3.getName(), part3.getDescription(), part3.getCode(), part3.getManufacturer());
        part.setId(0);
        int manufacturerId = 333;
        Mockito.when(partRepository.save(part)).thenReturn(part3);
        Mockito.when(manufacturerRepository.findById(manufacturerId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> partService.createPart(part));
    }

    @Test
    void updatePart_WithProperData_PartUpdated() {
        Part part = new Part(part1.getId(), part1.getName(), part1.getDescription(), part1.getCode(), part1.getManufacturer());
        int id = part2.getId();
        int manufacturerId = part.getManufacturer().getId();
        part.setId(id);
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.of(part2));
        Mockito.when(partRepository.save(part)).thenReturn(part);
        Mockito.when(manufacturerRepository.findById(manufacturerId)).thenReturn(Optional.of(manufacturer));
        assertEquals(part, partService.updatePart(part1, id));
    }

    @Test
    void updatePart_WithWrongData_ExceptionThrown() {
        Part part = new Part(part1.getId(), part1.getName(), part1.getDescription(), part1.getCode(), part1.getManufacturer());
        int id = part2.getId();
        int manufacturerId = part.getManufacturer().getId();
        part.setId(id);
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.of(part2));
        Mockito.when(partRepository.save(part)).thenReturn(part);
        Mockito.when(manufacturerRepository.findById(manufacturerId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> partService.updatePart(part1, id));
    }

    @Test
    void deletePart_WithProperData_PartDeleted() {
        int id = part1.getId();
        Mockito.when(partRepository.existsById(id)).thenReturn(true);
        partService.deletePart(id);
        Mockito.verify(partRepository).deleteById(id);
    }

    @Test
    void deletePart_WithWrongData_ExceptionThrown() {
        int id = part1.getId();
        Mockito.when(partRepository.existsById(id)).thenReturn(false);
        assertThrows(EntityByIdNotFoundException.class, () -> partService.deletePart(id));
    }

    @Test
    void sendPartToMQ_WithProperId_PartSent() {
        int id = part1.getId();
        Mockito.when(mqService.sendEntityToMQ(part1)).thenReturn(String.format("%s sent to MQ", part1.getClass().getSimpleName()));
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.of(part1));
        assertEquals(String.format("%s sent to MQ", part1.getClass().getSimpleName()), partService.sendPartToMQ(id));
    }

    @Test
    void sendPartToMQ_WithWrongId_ExceptionThrown() {
        int id = 222;
        Mockito.when(mqService.sendEntityToMQ(part2)).thenReturn(String.format("%s sent to MQ", part1.getClass().getSimpleName()));
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> partService.sendPartToMQ(id));
    }

    @Test
    void sendPartToMQAndDelete_WithProperId_PartSentAndDeleted() {
        int id = part2.getId();
        Mockito.when(mqService.sendEntityToMQ(part2)).thenReturn(String.format("%s sent to MQ", part2.getClass().getSimpleName()));
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.of(part2));
        Mockito.when(partRepository.existsById(id)).thenReturn(true);
        assertEquals(String.format("%s sent to MQ and was deleted", part2.getClass().getSimpleName()), partService.sendPartToMQAndDelete(id));
    }

    @Test
    void sendPartToMQAndDelete_WithWrongId_ExceptionThrown() {
        int id = 455;
        Mockito.when(mqService.sendEntityToMQ(part2)).thenReturn(String.format("%s sent to MQ", part2.getClass().getSimpleName()));
        Mockito.when(partRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(partRepository.existsById(id)).thenReturn(false);
        assertThrows(EntityByIdNotFoundException.class, () -> partService.sendPartToMQAndDelete(id));
    }

    @Test
    void getPartFromMQ_WithProperId_PartSent() {
        Mockito.when(mqService.getEntityFromMQ(Part.class)).thenReturn(part2);
        Mockito.when(partRepository.save(part2)).thenReturn(part2);
        assertEquals(part2, partService.getPartFromMQ());
    }
    @Test
    void getPartFromMQ_WithDuplicatedPart_ExceptionThrown() {
        Mockito.when(mqService.getEntityFromMQ(Part.class)).thenReturn(part2);
        Mockito.when(partRepository.save(part2))
                .thenThrow(new ConstraintViolationException("Duplicate entry 'hjkjkuuj' for key 'part.name_UNIQUE'",
                        new SQLException("SQL Error: 1062, SQLState: 23000"), "part.name_UNIQUE"));
        assertThrows(ConstraintViolationException.class, () -> partService.getPartFromMQ());

    }
}