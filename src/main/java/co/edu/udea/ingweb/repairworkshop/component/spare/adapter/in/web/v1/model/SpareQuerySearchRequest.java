package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpareQuerySearchRequest {

    private String name;

    private String description;

    private Long unitPriceGreaterThan;

    private Long unitCostGreaterThan;

    private Long stockGreaterThan;

    private Long unitPriceLessThan;

    private Long unitCostLessThan;

    private Long stockLessThan;

    public static SpareQuerySearchCmd toModel(SpareQuerySearchRequest queryCriteria){
        return SpareQuerySearchCmd.builder()
                .name(queryCriteria.getName())
                .description(queryCriteria.getDescription())
                .stockGreaterThan(queryCriteria.getStockGreaterThan())
                .stockLessThan(queryCriteria.getStockLessThan())
                .unitCostGreaterThan(queryCriteria.getUnitCostGreaterThan())
                .unitCostLessThan(queryCriteria.getUnitCostLessThan())
                .unitPriceGreaterThan(queryCriteria.getUnitPriceGreaterThan())
                .unitPriceLessThan(queryCriteria.getUnitPriceLessThan())
                .build();
    }
}
