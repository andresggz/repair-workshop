package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleQuerySearchRequest {

    private Maker maker;

    private VehicleType vehicleType;

    private String model;

    public static VehicleQuerySearchCmd toModel(VehicleQuerySearchRequest queryCriteria){
        return VehicleQuerySearchCmd.builder()
                .maker(queryCriteria.getMaker())
                .vehicleType(queryCriteria.getVehicleType())
                .model(queryCriteria.getModel())
                .build();
    }
}
