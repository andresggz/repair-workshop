package co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model;

import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpareQuerySearchCmd {

    private String name;

    private String description;

    private Long unitPriceGreaterThan;

    private Long unitCostGreaterThan;

    private Long stockGreaterThan;

    private Long unitPriceLessThan;

    private Long unitCostLessThan;

    private Long stockLessThan;
}
