package co.edu.udea.ingweb.repairworkshop.component.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long createdBy;

    private LocalDateTime createdAt;

    private boolean active;

    private Long updatedBy;

    private LocalDateTime updatedAt;


}
