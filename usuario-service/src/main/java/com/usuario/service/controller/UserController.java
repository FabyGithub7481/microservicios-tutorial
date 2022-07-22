package com.usuario.service.controller;

import com.usuario.service.entity.UserTest;
import com.usuario.service.model.Car;
import com.usuario.service.model.Motorcycle;
import com.usuario.service.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public ResponseEntity<List<UserTest>> listarUsers() {
    List<UserTest> userTests = userService.getAll();
    if (userTests.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(userTests);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserTest> getUser(@PathVariable("id") long id) {
    UserTest userTest = userService.getUserById(id);
    if (userTest == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userTest);
  }

  @PostMapping
  public ResponseEntity<UserTest> saveUser(@RequestBody UserTest userTest) {
    UserTest newUserTest = userService.save(userTest);
    return ResponseEntity.ok(newUserTest);

  }

  @GetMapping("/cars/{userId}")
  public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long id) {
    UserTest userTest = userService.getUserById(id);
    if (userTest == null) {
      return ResponseEntity.notFound().build();
    }
    List<Car> cars = userService.getCars(id);
    return ResponseEntity.ok(cars);
  }

  @GetMapping("/motorcycles/{userId}")
  public ResponseEntity<List<Motorcycle>> getMotorcycles(@PathVariable("userId") Long id) {
    UserTest userTest = userService.getUserById(id);
    if (userTest == null) {
      return ResponseEntity.notFound().build();
    }
    List<Motorcycle> motorcycles = userService.getMotorcycles(id);
    return ResponseEntity.ok(motorcycles);
  }

  @PostMapping("/car/{userId}")
  public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
    Car newCar = userService.saveCar(userId, car);
    return ResponseEntity.ok(newCar);
  }

  @PostMapping("/motorcycle/{userId}")
  public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable("userId") Long userId,
      @RequestBody Motorcycle motorcycle) {
    Motorcycle newMotorcycle = userService.saveMotorcycle(userId, motorcycle);
    return ResponseEntity.ok(newMotorcycle);
  }

  @GetMapping("/todos/{userId}")
  public ResponseEntity<Map<String, Object>> listarAllVehicles(
      @PathVariable("userId") Long userId) {
    Map<String, Object> resultado = userService.getUserAndVehicles(userId);
    return ResponseEntity.ok(resultado);
  }
}
