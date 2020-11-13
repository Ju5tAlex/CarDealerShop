package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.ClientRepository;
import org.example.task.cardealershop.dao.ManagerRepository;
import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(int id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException("Client", id));
    }

    @Override
    public Client createClient(Client client) {
        client.setId(0);
        client.setManager(getManager(client));
        return clientRepository.save(client);
    }

    private Manager getManager(Client client) {
        int managerId = client.getManager().getId();
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new EntityByIdNotFoundException("Manager", managerId));
    }

    @Override
    public Client updateClient(Client updatedClient, int id) {
        return clientRepository.findById(id)
                .map((client) -> {
                    client.setFirstName(updatedClient.getFirstName());
                    client.setLastName(updatedClient.getLastName());
                    client.setEmail(updatedClient.getEmail());
                    client.setPhoneNumber(updatedClient.getPhoneNumber());
                    client.setManager(getManager(updatedClient));
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Client", id));
    }

    @Override
    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }
}
