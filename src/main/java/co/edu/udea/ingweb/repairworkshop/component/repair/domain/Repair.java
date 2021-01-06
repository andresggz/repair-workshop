package co.edu.udea.ingweb.repairworkshop.component.repair.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.config.security.AuditSecurityConfiguration;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "repairs")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Repair implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private RepairState state;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String commentary;

    @ManyToMany
    private Set<User> repairmen;

    @ManyToOne
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RepairLine> repairLines;

    private Long totalPrice;

    private Long totalCost;

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
