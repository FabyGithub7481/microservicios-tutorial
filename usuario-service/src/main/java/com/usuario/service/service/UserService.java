package com.usuario.service.service;

import com.usuario.service.entity.UserTest;
import com.usuario.service.feignclients.CarFeignClient;
import com.usuario.service.feignclients.MotorcycleFeignClient;
import com.usuario.service.model.Car;
import com.usuario.service.model.Motorcycle;
import com.usuario.service.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CarFeignClient carFeignClient;

  @Autowired
  private MotorcycleFeignClient motorcycleFeignClient;

  @Autowired
  private UserRepository userRepository;

  public List<UserTest> getAll() {
    return userRepository.findAll();
  }

  public UserTest getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public UserTest save(UserTest userTest) {
    UserTest newUserTest = userRepository.save(userTest);
    return newUserTest;
  }

  public List<Car> getCars(Long userId) {
    List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/user/" + userId, List.class);
    return cars;
  }

  public List<Motorcycle> getMotorcycles(Long userId) {
    List<Motorcycle> motorcycles = restTemplate.getForObject("http://localhost:8003/motorcycle/user/" + userId, List.class);
    return motorcycles;
  }

  public Car saveCar(Long userId, Car car) {
    car.setUserId(userId);
    Car newCar = carFeignClient.save(car);
    return newCar;
  }

  public Motorcycle saveMotorcycle(Long userId, Motorcycle motorcycle) {
    motorcycle.setUserId(userId);
    Motorcycle newMotorcycle = motorcycleFeignClient.save(motorcycle);
    return newMotorcycle;
  }

  public Map<String, Object> getUserAndVehicles(Long userId){
    Map<String,Object> resultado= new HashMap<>();
    UserTest userTest= userRepository.findById(userId).orElse(null);
    if (userTest==null){
      resultado.put("Mensaje: ","El usuario no existe");
      return resultado;
    }
    resultado.put("User",userTest);

    List<Car> cars= carFeignClient.getCars(userId);
    if (cars.isEmpty()){
      resultado.put("Mensaje Carros: ","El usuario no tiene carros");
    }
    else{
      resultado.put("Carros: ",cars);
    }

    List<Motorcycle> motorcycles= motorcycleFeignClient.getMotorcycles(userId);
    if (motorcycles.isEmpty())
    {
      resultado.put("Mensaje Motos: ", "El usuario no tiene motos");
    }
    else {
      resultado.put("Motos: ",motorcycles);
    }

    return  resultado;
  }
}
