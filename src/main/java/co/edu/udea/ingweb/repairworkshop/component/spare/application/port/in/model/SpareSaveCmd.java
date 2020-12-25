package co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
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
public class SpareSaveCmd {

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

    private Long userIdAuthenticated;

    public static Spare toModel(SpareSaveCmd spareToRegister){
        return Spare.builder()
                .name(spareToRegister.getName())
                .description(spareToRegister.getDescription())
                .unitPrice(spareToRegister.getUnitPrice())
                .unitCost(spareToRegister.getUnitCost())
                .stock(spareToRegister.getStock())
                .build();
    }

}
