package org.example.task.cardealershop.dao;

import org.example.task.cardealershop.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Integer> {
}
