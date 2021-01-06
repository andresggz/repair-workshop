package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareItemListResponse {

    private Long id;

    private Long spareId;

    private Long unitPrice;

    private Long unitCost;

    private Integer quantity;

    private Long totalPrice;

    public static SpareItemListResponse fromModel(SpareItem spareItem){
        return SpareItemListResponse.builder()
                .id(spareItem.getId()).spareId(spareItem.getSpare().getId())
                .unitPrice(spareItem.getUnitPrice()).unitCost(spareItem.getUnitCost())
                .quantity(spareItem.getQuantity()).totalPrice(spareItem.getTotalPrice())
                .build();
    }
}
