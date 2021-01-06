package co.edu.udea.ingweb.repairworkshop.component.repair.domain;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import co.edu.udea.ingweb.repairworkshop.config.security.AuditSecurityConfiguration;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repair_lines")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RepairLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<SpareItem> spareItems = new HashSet<>();

    private Long workforcePrice;

    private Long totalSparePrice;

    private Long totalSpareCost;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Long repairId;

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
