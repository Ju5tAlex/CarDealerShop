package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ClientRepository;
import org.example.task.cardealershop.dao.ManagerRepository;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.junit.Assert;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ManagerRepository managerRepository;

    @Autowired
    private ClientService clientService;

    private Manager manager;
    private Client client1;
    private Client client2;
    private Client client3;
    private List<Client> clients;

    @BeforeEach
    public void beforeTest() {
        manager = new Manager("Vasya", "Petrov", "petrov@gmail.com", "88005553555");
        client1 = new Client(51,"Danya", "Deloviy", "delovoy@gmail.com", "74443256577", manager);
        client2 = new Client(52,"Sema", "Dankov", "dankov@gmail.com", "65436728832", manager);
        client3 = new Client(53, "Rice", "Filed", "ricefield@welcome.mf", "87776666969", manager);
    }

    @Test
    public void getAllClients_ListContainsClients_SizeEqual() {
        clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client1);
        clients.add(client1);
        when(clientRepository.findAll()).thenReturn(clients);
        assertEquals(clients.size(), clientRepository.findAll().size());
    }

    @Test
    public void getClient_WithExistingId_PartFound() {
        int clientId = client2.getId();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client2));
        Assert.assertEquals(client2, clientService.getClient(clientId));
    }

    @Test
    void getClient_WithNonExistingId_ExceptionThrown() {
        int clientId = 69;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> clientService.getClient(clientId));
    }

    @Test
    public void getClientsManager_WithExistingId_ManagerFound() {
        int clientId = client1.getId();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client1));
        assertEquals(manager, clientService.getClientsManager(clientId));
    }

    @Test
    void getClientsManager_WithNonExistingId_ExceptionThrown() {
        int clientId = 34;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> clientService.getClientsManager(clientId));
    }

    @Test
    public void createClient_WithProperData_PartCreated() {
        Client client = new Client(client3.getId(), client3.getFirstName(), client3.getLastName(), client3.getEmail(),
                client3.getPhoneNumber(), client3.getManager());
        client.setId(0);
        int managerId = manager.getId();
        when(clientRepository.save(client)).thenReturn(client3);
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));
        assertEquals(client3, clientService.createClient(client));
    }

    @Test
    void createClient_WithWrongData_ExceptionThrown() {
        Client client = new Client(client3.getId(), client3.getFirstName(), client3.getLastName(), client3.getEmail(),
                client3.getPhoneNumber(), client3.getManager());
        client.setId(0);
        int managerId = manager.getId();
        when(clientRepository.save(client)).thenReturn(client3);
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> clientService.createClient(client));
    }

    @Test
    public void updateClient_WithProperData_PartUpdated() {
        Client client = new Client(client1.getId(), client1.getFirstName(), client1.getLastName(), client1.getEmail(),
                client1.getPhoneNumber(), client1.getManager());
        int clientId = client2.getId();
        int managerId = client.getManager().getId();
        client.setId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client2));
        when(clientRepository.save(client)).thenReturn(client);
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));
        assertEquals(client, clientService.updateClient(client1, clientId));
    }

    @Test
    void updatePart_WithWrongData_ExceptionThrown() {
        Client client = new Client(client1.getId(), client1.getFirstName(), client1.getLastName(), client1.getEmail(),
                client1.getPhoneNumber(), client1.getManager());
        int clientId = client2.getId();
        int managerId = client.getManager().getId();
        client.setId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client2));
        when(clientRepository.save(client)).thenReturn(client);
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());
        assertThrows(EntityByIdNotFoundException.class, () -> clientService.updateClient(client1, clientId));
    }

    @Test
    public void deleteClient_WithProperData_PartDeleted() {
        int id = client1.getId();
        Mockito.when(clientRepository.existsById(id)).thenReturn(true);
        clientService.deleteClient(id);
        Mockito.verify(clientRepository).deleteById(id);
    }

    @Test
    void deletePart_WithWrongData_ExceptionThrown() {
        int id = client1.getId();
        Mockito.when(clientRepository.existsById(id)).thenReturn(false);
        assertThrows(EntityByIdNotFoundException.class, () -> clientService.deleteClient(id));
    }
}