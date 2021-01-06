package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model;


import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareSaveResponse {

    private Long id;

    private String name;

    private String description;

    private Long unitPrice;

    private Long unitCost;

    private Long stock;

    private boolean active;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static SpareSaveResponse fromModel(Spare spare){
        return SpareSaveResponse.builder()
                .id(spare.getId()).name(spare.getName())
                .description(spare.getDescription()).unitPrice(spare.getUnitPrice())
                .unitCost(spare.getUnitCost()).stock(spare.getStock())
                .active(spare.isActive()).createdBy(spare.getCreatedBy())
                .createdAt(spare.getCreatedAt()).updatedBy(spare.getUpdatedBy())
                .updatedAt(spare.getUpdatedAt()).build();
    }
}
