package co.edu.udea.ingweb.repairworkshop.component.spare.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Spare spare;

    @NotNull
    private Long unitPrice;

    @NotNull
    private Long unitCost;

    private Integer quantity;

    private Long totalPrice;

    private Long totalCost;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;
}
