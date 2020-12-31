package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareItemSaveRequest {

    @NotNull
    private Long spareId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public static SpareItemSaveCmd toModel(SpareItemSaveRequest spareItem){
        return SpareItemSaveCmd.builder()
                .spareId(spareItem.getSpareId())
                .quantity(spareItem.getQuantity())
                .build();
    }
}
