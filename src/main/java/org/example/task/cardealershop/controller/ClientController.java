package org.example.task.cardealershop.controller;

import org.example.task.cardealershop.dto.ClientDTO;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;
import org.example.task.cardealershop.service.ClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return modelMapper.map(clientService.getAllClients(), new TypeToken<List<ClientDTO>>(){}.getType());
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable int id) {
        return modelMapper.map(clientService.getClient(id), ClientDTO.class);
    }

    @GetMapping("/{id}/manager")
    public Manager getClientsManager(@PathVariable int id) {
        return clientService.getClientsManager(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);
        return modelMapper.map(clientService.createClient(client), ClientDTO.class);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO, @PathVariable int id) {
        Client client = modelMapper.map(clientDTO, Client.class);
        return modelMapper.map(clientService.updateClient(client, id), ClientDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }
}
