package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairSaveRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String commentary;

    private Set<Long> repairmanIds;

    @NotNull
    private Long ownerId;

    @NotNull
    private Long vehicleId;

    public static RepairSaveCmd toModel(RepairSaveRequest repairToRegister){
        return RepairSaveCmd.builder()
                .commentary(repairToRegister.getCommentary())
                .repairmanIds(repairToRegister.getRepairmanIds())
                .ownerId(repairToRegister.getOwnerId())
                .vehicleId(repairToRegister.getVehicleId())
                .build();
    }
}
