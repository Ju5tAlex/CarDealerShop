package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ClientRepository;
import org.example.task.cardealershop.dao.ManagerRepository;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Client> getAllClients() {
        logger.info("Getting list of all clients from database");
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(int id) {
        logger.info(String.format("Getting a client with id=%d from database", id));
        return clientRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException("Client", id));
    }

    @Override
    public Manager getClientsManager(int id) {
        logger.info(String.format("Getting a manager of a client with id=%d from database", id));
        return getClient(id).getManager();
    }

    @Override
    public Client createClient(Client client, int managerId) {
        logger.info(String.format("Creating a new client with manager which id=%d", managerId));
        client.setId(0);
        client.setManager(getManager(managerId));
        return clientRepository.save(client);
    }

    private Manager getManager(int managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new EntityByIdNotFoundException("Manager", managerId));
    }

    @Override
    public Client updateClient(Client updatedClient, int id, int managerId) {
        logger.info(String.format("Updating info for a client with id=%d", id));
        return clientRepository.findById(id)
                .map((client) -> {
                    client.setFirstName(updatedClient.getFirstName());
                    client.setLastName(updatedClient.getLastName());
                    client.setEmail(updatedClient.getEmail());
                    client.setPhoneNumber(updatedClient.getPhoneNumber());
                    client.setManager(getManager(managerId));
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Client", id));
    }

    @Override
    public void deleteClient(int id) {
        logger.info(String.format("Deleting a client with id=%d", id));
        if (!clientRepository.existsById(id)) throw new EntityByIdNotFoundException("Client", id);
        clientRepository.deleteById(id);
    }
}
