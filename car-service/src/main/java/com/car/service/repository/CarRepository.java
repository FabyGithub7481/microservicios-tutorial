package com.car.service.repository;

import com.car.service.entity.Car;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository <Car, Long>{

  List<Car> findCarByUserId(int userId);

}
