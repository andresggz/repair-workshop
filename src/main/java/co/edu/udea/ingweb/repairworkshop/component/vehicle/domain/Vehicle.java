package co.edu.udea.ingweb.repairworkshop.component.vehicle.domain;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.config.security.AuditSecurityConfiguration;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Maker maker;

    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String model;

    @ManyToMany
    private Set<User> owners = new HashSet<>();

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String licensePlate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Repair> repairs = new HashSet<>();

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.createdBy = AuditSecurityConfiguration.getDataAuthenticatedUser();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = AuditSecurityConfiguration.getDataAuthenticatedUser();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = AuditSecurityConfiguration.getDataAuthenticatedUser();
    }
}
