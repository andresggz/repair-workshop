package co.edu.udea.ingweb.repairworkshop.component.vehicle.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "makers")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 25)
    private String name;

    @NotBlank
    @Size(min = 2, max = 25)
    private String description;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;
}
