package co.edu.udea.ingweb.repairworkshop.component.repair.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "repairs")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RepairState state;

    @NotBlank
    @Size(min = 3, max = 50)
    private String commentary;

    @ManyToMany
    private Set<User> repairmen;

    @ManyToOne
    private User ownerEnteredBy;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RepairLine> repairLines;

    private BigDecimal totalPrice;

    private BigDecimal totalCost;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;

}
