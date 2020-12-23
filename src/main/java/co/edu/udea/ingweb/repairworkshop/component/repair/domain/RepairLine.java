package co.edu.udea.ingweb.repairworkshop.component.repair.domain;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "repair_lines")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RepairLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<SpareItem> spareItems;

    private BigDecimal workforcePrice;

    private BigDecimal spareTotalPrice;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;
}
