package org.example.task.cardealershop.dao;

import org.example.task.cardealershop.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface PartRepository extends JpaRepository<Part, Integer> {
}
