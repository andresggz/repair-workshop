package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairUpdateStateCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairUpdateStateRequest {

    private RepairState state;

    public static RepairUpdateStateCmd toModel(RepairUpdateStateRequest repairToUpdate){
        return RepairUpdateStateCmd.builder()
                .state(repairToUpdate.getState())
                .build();
    }
}
