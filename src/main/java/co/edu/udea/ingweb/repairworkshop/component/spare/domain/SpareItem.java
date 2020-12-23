package co.edu.udea.ingweb.repairworkshop.component.spare.domain;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spare_items")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SpareItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Spare spare;

    @NotNull
    private BigDecimal unitPrice;

    @NotNull
    private BigDecimal unitCost;

    private Long quantity;

    private BigDecimal totalPrice;

    private BigDecimal totalCost;

    @ManyToOne
    private User createdBy;

    private LocalDateTime createdAt;

    @ManyToOne
    private User updatedBy;

    private LocalDateTime updatedAt;
}
