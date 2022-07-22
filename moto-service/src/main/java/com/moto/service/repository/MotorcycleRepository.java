package com.moto.service.repository;

import com.moto.service.entity.Motorcycle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

  List<Motorcycle> findMotoByUserId(int userId);

}
