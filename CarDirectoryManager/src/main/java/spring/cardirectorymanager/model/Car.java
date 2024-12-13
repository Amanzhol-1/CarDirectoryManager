package spring.cardirectorymanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import spring.cardirectorymanager.validation.ValidYear;

import java.io.Serializable;
import java.time.Year;
import java.util.UUID;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Car implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "make", nullable = false)
    private String make;

    @NotBlank
    @Column(name = "model", nullable = false)
    private String model;

    @ValidYear
    @Column(name = "year", nullable = false)
    private int year;

    @Positive
    @Column(name = "price", nullable = false)
    private double price;

    @NotBlank
    @Size(min = 17, max = 17)
    @Column(name = "vin", unique = true, nullable = false, length = 17)
    private String vin;
}

