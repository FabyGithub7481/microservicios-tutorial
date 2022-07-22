package com.moto.service.service;

import com.moto.service.entity.Motorcycle;
import com.moto.service.repository.MotorcycleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotorcycleService {

  @Autowired
  private MotorcycleRepository motorcycleRepository;


  public List<Motorcycle> getAll(){
    return motorcycleRepository.findAll();
  }
  public Motorcycle getMotorcycleById(Long id){
    return motorcycleRepository.findById(id).orElse(null);
  }

  public Motorcycle save(Motorcycle motorcycle){
    Motorcycle newMotorcycle = motorcycleRepository.save(motorcycle);
    return newMotorcycle;
  }

  public List<Motorcycle> getMotorcycleByUserId(int userId){
    return motorcycleRepository.findMotoByUserId(userId);
  }
}
