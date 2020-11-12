package org.example.task.cardealershop.service;

import org.example.task.cardealershop.dao.CarRepository;
import org.example.task.cardealershop.entity.Car;
import org.example.task.cardealershop.exception.EntityByIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCar(int id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityByIdNotFoundException("Car", id));
    }

    @Override
    public Car createCar(Car car) {
        car.setId(0);
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car updatedCar, int id) {
        return carRepository.findById(id)
                .map((car) -> {
                    car.setModelName(updatedCar.getModelName());
                    car.setPrice(updatedCar.getPrice());
                    car.setEnginePower(updatedCar.getEnginePower());
                    car.setEnginePower(updatedCar.getEnginePower());
                    car.setColour(updatedCar.getColour());
                    return carRepository.save(car);
                })
                .orElseThrow(() -> new EntityByIdNotFoundException("Car", id));
    }

    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
