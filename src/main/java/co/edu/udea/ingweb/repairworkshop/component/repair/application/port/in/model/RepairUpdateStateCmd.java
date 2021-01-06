package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairUpdateStateCmd {

    private RepairState state;
}
