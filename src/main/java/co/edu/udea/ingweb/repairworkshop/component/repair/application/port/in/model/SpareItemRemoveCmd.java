package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpareItemRemoveCmd {

    @NotNull
    private Long spareItemId;

    @NotNull
    private Long repairLineId;
}
