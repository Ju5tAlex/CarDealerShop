package org.example.task.cardealershop.dao;

import org.example.task.cardealershop.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
