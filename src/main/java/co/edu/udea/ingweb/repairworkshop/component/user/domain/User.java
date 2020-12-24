package co.edu.udea.ingweb.repairworkshop.component.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String dni;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String names;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30)
    private String lastNames;

    @NotBlank
    @Size(min = 6, max = 15)
    private String phoneNumber;

    @NotBlank
    private String profilePhoto;

    @Email
    @NotNull
    private String primaryEmailAddress;

    @NotNull
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    private boolean active;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;


}
