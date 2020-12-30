package co.edu.udea.ingweb.repairworkshop.component.repair.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;

}
