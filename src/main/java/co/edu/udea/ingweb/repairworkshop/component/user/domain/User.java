package co.edu.udea.ingweb.repairworkshop.component.user.domain;

import co.edu.udea.ingweb.repairworkshop.config.security.AuditSecurityConfiguration;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String dni;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String names;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String lastNames;

    @Size(min = 6, max = 15)
    private String phoneNumber;

    private String profilePhoto;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

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
