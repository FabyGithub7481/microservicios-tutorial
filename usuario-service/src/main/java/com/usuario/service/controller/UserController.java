package com.usuario.service.controller;

import com.usuario.service.entity.UserTest;
import com.usuario.service.model.Car;
import com.usuario.service.model.Motorcycle;
import com.usuario.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
  @GetMapping("/cars/{userId}")
  public ResponseEntity<List<Car>> getCars(@PathVariable("userId") Long id) {
    UserTest userTest = userService.getUserById(id);
    if (userTest == null) {
      return ResponseEntity.notFound().build();
    }
    List<Car> cars = userService.getCars(id);
    return ResponseEntity.ok(cars);
  }

  @CircuitBreaker(name = "motorcycleCB", fallbackMethod = "fallBackGetMotorcycle")
  @GetMapping("/motorcycles/{userId}")
  public ResponseEntity<List<Motorcycle>> getMotorcycles(@PathVariable("userId") Long id) {
    UserTest userTest = userService.getUserById(id);
    if (userTest == null) {
      return ResponseEntity.notFound().build();
    }
    List<Motorcycle> motorcycles = userService.getMotorcycles(id);
    return ResponseEntity.ok(motorcycles);
  }

  @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCars")
  @PostMapping("/car/{userId}")
  public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
    Car newCar = userService.saveCar(userId, car);
    return ResponseEntity.ok(newCar);
  }

  @CircuitBreaker(name = "motorcycleCB", fallbackMethod = "fallBackSaveMotorcycle")
  @PostMapping("/motorcycle/{userId}")
  public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable("userId") Long userId,@RequestBody Motorcycle motorcycle) {
    Motorcycle newMotorcycle = userService.saveMotorcycle(userId, motorcycle);
    return ResponseEntity.ok(newMotorcycle);
  }

  @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetAll")
  @GetMapping("/todos/{userId}")
  public ResponseEntity<Map<String, Object>> listarAllVehicles(@PathVariable("userId") Long userId) {
    Map<String, Object> resultado = userService.getUserAndVehicles(userId);
    return ResponseEntity.ok(resultado);
  }
  private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") Long id, RuntimeException exception){
    return new ResponseEntity("El usuario: " + id + " iene los carros en el taller!!", HttpStatus.OK);
  }
  private ResponseEntity<Car> fallBackSaveCars(@PathVariable("userId") Long id,@RequestBody Car car, RuntimeException exception){
    return new ResponseEntity("El usuario: " + id + " No Tiene dinero para los carros!!", HttpStatus.OK);
  }
  private ResponseEntity<List<Motorcycle>> fallBackGetMotorcycle(@PathVariable("userId") Long id, RuntimeException exception){
    return new ResponseEntity("El usuario: " + id + " Tiene las motos en el taller!!", HttpStatus.OK);
  }
  private ResponseEntity<Motorcycle> fallBackSaveMotorcycle(@PathVariable("userId") Long id,@RequestBody Motorcycle motorcycle, RuntimeException exception){
    return new ResponseEntity("El usuario: " + id + " No Tiene dinero para las motos!!", HttpStatus.OK);
  }
  private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") Long id, RuntimeException exception){
    return new ResponseEntity("El usuario: " + id + " No Tiene vehiculos!!", HttpStatus.OK);
  }
}
