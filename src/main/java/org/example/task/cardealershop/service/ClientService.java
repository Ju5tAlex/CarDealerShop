package org.example.task.cardealershop.service;

import org.example.task.cardealershop.entity.Client;
import org.example.task.cardealershop.entity.Manager;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client getClient(int id);

    Manager getClientsManager(int id);

    Client createClient(Client client);

    Client updateClient(Client updatedClient, int id);

    void deleteClient(int id);
}
