package org.example.task.cardealershop.dao;

import org.example.task.cardealershop.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
