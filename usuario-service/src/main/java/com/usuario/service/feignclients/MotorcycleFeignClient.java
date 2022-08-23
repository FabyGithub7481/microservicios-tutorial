package com.usuario.service.feignclients;

import com.usuario.service.model.Motorcycle;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "moto-service")
@RequestMapping("/motorcycle")
public interface MotorcycleFeignClient {

  @PostMapping
  Motorcycle save(Motorcycle motorcycle);


  @GetMapping("/user/{userId}")
  List<Motorcycle> getMotorcycles(@PathVariable("userId") Long userId);

}
