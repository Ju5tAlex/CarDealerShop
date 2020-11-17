package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;
import org.example.task.cardealershop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/clients",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable int id) {
        return clientService.getClient(id);
    }

    @GetMapping("/{id}/manager")
    public Manager getClientsManager(@PathVariable int id) {
        return clientService.getClientsManager(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Client createClient(@RequestBody Client client, @RequestParam("manager_id") int managerId) {
        return clientService.createClient(client, managerId);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Client updateClient(@RequestBody Client client, @PathVariable int id, @RequestParam("manager_id") int managerId) {
        return clientService.updateClient(client, id, managerId);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }
}
