package co.edu.udea.ingweb.repairworkshop.component.spare.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spares")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Spare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private String description;

    @NotNull
    private BigDecimal price;

    private Long stock;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;
}
