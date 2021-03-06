package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleQuerySearchCmd {

    private Maker maker;

    private VehicleType vehicleType;

    private String model;
}
