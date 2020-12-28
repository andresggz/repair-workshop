package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleListResponse {

    private Long id;

    private Maker maker;

    private VehicleType vehicleType;

    private String model;

    public static VehicleListResponse fromModel(Vehicle vehicle){
        return VehicleListResponse.builder()
                .id(vehicle.getId()).maker(vehicle.getMaker())
                .vehicleType(vehicle.getVehicleType())
                .model(vehicle.getModel()).build();
    }
}
