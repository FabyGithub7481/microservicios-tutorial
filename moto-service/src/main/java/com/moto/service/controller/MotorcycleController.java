package com.moto.service.controller;

import com.moto.service.entity.Motorcycle;
import com.moto.service.service.MotorcycleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("motorcycle")
public class MotorcycleController {

  @Autowired
  private MotorcycleService motorcycleService;

  @GetMapping
  public ResponseEntity<List<Motorcycle>> listarMotorcycles(){
    List<Motorcycle> motorcycles = motorcycleService.getAll();
    if (motorcycles.isEmpty()){
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(motorcycles);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable("id") long id )
  {
    Motorcycle motorcycle = motorcycleService.getMotorcycleById(id);
    if (motorcycle ==null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(motorcycle);
  }

  @PostMapping
  public ResponseEntity<Motorcycle> saveMotorcycle(@RequestBody Motorcycle motorcycle){
    Motorcycle newMotorcycle = motorcycleService.save(motorcycle);
    return ResponseEntity.ok(newMotorcycle);

  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Motorcycle>> listarMotorcycleByUserId(@PathVariable("userId") int id){
    List<Motorcycle> motorcycles= motorcycleService.getMotorcycleByUserId(id);
    if (motorcycles.isEmpty())
    {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(motorcycles);
  }


}
