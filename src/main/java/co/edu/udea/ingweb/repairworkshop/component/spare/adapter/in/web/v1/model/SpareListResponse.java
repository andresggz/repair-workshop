package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareListResponse {

    private Long id;

    private String name;

    private String description;

    private Long unitPrice;

    private Long unitCost;

    private Long stock;

    private boolean active;

    public static SpareListResponse fromModel(Spare spare){
        return SpareListResponse.builder()
                .id(spare.getId()).name(spare.getName())
                .description(spare.getDescription()).unitPrice(spare.getUnitPrice())
                .unitCost(spare.getUnitCost()).stock(spare.getStock())
                .active(spare.isActive()).build();
    }
}
