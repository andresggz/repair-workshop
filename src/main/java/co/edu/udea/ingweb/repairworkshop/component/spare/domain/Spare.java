package co.edu.udea.ingweb.repairworkshop.component.spare.domain;

import co.edu.udea.ingweb.repairworkshop.config.security.AuditSecurityConfiguration;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "spares")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Spare implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private String description;

    @NotNull
    @Min(value = 0)
    private Long unitPrice;

    @NotNull
    @Min(value = 0)
    private Long unitCost;

    @NotNull
    @Min(value = 0)
    private Long stock;

    private boolean active;

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
