package com.car.service.service;

import com.car.service.entity.Car;
import com.car.service.repository.CarRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

  @Autowired
  private CarRepository carRepository;

  public List<Car> getAll(){
    return carRepository.findAll();
  }
  public Car getCarById(Long id){
    return carRepository.findById(id).orElse(null);
  }

  public Car save(Car car){
    Car newCar = carRepository.save(car);
    return newCar;
  }

  public List<Car> getCarByUserId(int userId){
    return carRepository.findCarByUserId(userId);
  }

}
