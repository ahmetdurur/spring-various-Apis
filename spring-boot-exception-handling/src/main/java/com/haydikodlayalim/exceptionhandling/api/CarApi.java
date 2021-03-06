package com.haydikodlayalim.exceptionhandling.api;

import com.haydikodlayalim.exceptionhandling.dto.Car;
import com.haydikodlayalim.exceptionhandling.exception.EntityNotFoundException;
import com.haydikodlayalim.exceptionhandling.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarApi {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<Car> getCar(@RequestParam String name){
        return ResponseEntity.ok(carService.getCar(name)) ;
    }

    /**
    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFound() {
        return "Record not found";
    }**/
}
