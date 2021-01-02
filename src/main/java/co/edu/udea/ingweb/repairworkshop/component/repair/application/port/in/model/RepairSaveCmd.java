package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
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
public class RepairSaveCmd {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String commentary;

    private Set<Long> repairmanIds;

    @NotNull
    private Long ownerId;

    @NotNull
    private Long vehicleId;

    public static Repair toModel(RepairSaveCmd repairToRegister){
        return Repair.builder()
                .commentary(repairToRegister.getCommentary())
                .build();
    }

}
