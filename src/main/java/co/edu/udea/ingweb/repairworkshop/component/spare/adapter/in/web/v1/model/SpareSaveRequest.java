package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareSaveCmd;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpareSaveRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private String description;

    @NotNull
    @Min(value = 0)
    private Long unitPrice;

    @NotNull
    @Min(value = 0)
    private Long unitCost;

    @NotNull
    @Min(value = 0)
    private Long stock;

    public static SpareSaveCmd toModel(SpareSaveRequest spareToRegister){
        return SpareSaveCmd.builder()
                .name(spareToRegister.getName())
                .description(spareToRegister.getDescription())
                .unitPrice(spareToRegister.getUnitPrice())
                .unitCost(spareToRegister.getUnitCost())
                .stock(spareToRegister.getStock())
                .build();
    }
}
