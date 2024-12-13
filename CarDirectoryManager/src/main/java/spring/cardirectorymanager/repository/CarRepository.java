package spring.cardirectorymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.cardirectorymanager.model.Car;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
