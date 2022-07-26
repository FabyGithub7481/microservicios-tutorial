package com.usuario.service.feignclients;

import com.usuario.service.model.Car;
import com.usuario.service.model.Motorcycle;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="car-service",url="http://localhost:8002")
@RequestMapping("/car")
public interface CarFeignClient {

  @PostMapping
  Car save(@RequestBody Car car);

  @GetMapping("/user/{userId}")
  List<Car> getCars(@PathVariable("userId") Long userId);

}
