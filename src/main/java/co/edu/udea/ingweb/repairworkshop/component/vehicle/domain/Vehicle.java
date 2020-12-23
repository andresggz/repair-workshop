package co.edu.udea.ingweb.repairworkshop.component.vehicle.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Maker maker;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String model;

    @ManyToMany
    private Set<User> owners;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;

}
