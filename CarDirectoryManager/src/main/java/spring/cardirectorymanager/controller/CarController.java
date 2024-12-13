package spring.cardirectorymanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.cardirectorymanager.model.Car;
import spring.cardirectorymanager.service.CarService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @Operation(summary = "Retrieve a list of all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Car.class))))
    })
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "Retrieve a car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable UUID id) {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Create a new car entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody @Valid Car car) {
        Car createdCar = carService.createCar(car);
        return ResponseEntity.ok(createdCar);
    }

    @Operation(summary = "Update an existing car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID or input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable UUID id, @RequestBody @Valid Car updatedCar) {
        Car car = carService.updateCar(id, updatedCar);
        return ResponseEntity.ok(car);
    }

    @Operation(summary = "Delete a car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}

