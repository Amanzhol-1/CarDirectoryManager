package spring.cardirectorymanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cardirectorymanager.model.Car;
import spring.cardirectorymanager.repository.CarRepository;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.debug("Fetching all cars. Count: {}", cars.size());
        return cars;
    }

    public Car getCarById(UUID id) {
        logger.debug("Fetching car with ID: {}", id);
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Car with ID {} not found.", id);
                    return new IllegalArgumentException("Car with id " + id + " not found");
                });
    }

    public Car createCar(Car car) {
        validateYear(car.getYear());
        Car savedCar = carRepository.save(car);
        logger.debug("Saved new car with ID: {}", savedCar.getId());
        return savedCar;
    }

    public Car updateCar(UUID id, Car updatedCar) {
        Car existingCar = getCarById(id);

        validateYear(updatedCar.getYear());

        Car updatedExistingCar = existingCar.toBuilder()
                .make(updatedCar.getMake())
                .model(updatedCar.getModel())
                .year(updatedCar.getYear())
                .price(updatedCar.getPrice())
                .vin(updatedCar.getVin())
                .build();

        Car savedCar = carRepository.save(updatedExistingCar);
        logger.debug("Updated car with ID: {}", savedCar.getId());
        return savedCar;
    }

    public void deleteCar(UUID id) {
        Car car = getCarById(id);
        carRepository.delete(car);
        logger.debug("Deleted car with ID: {}", id);
    }

    private void validateYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 1886 || year > currentYear) {
            logger.error("Invalid year: {}. Must be between 1886 and {}", year, currentYear);
            throw new IllegalArgumentException("Year must be between 1886 and " + currentYear);
        }
    }
}

