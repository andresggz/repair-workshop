package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareItemSaveCmd {

    private Long spareId;

    private Long repairLineId;

    private Integer quantity;
}
