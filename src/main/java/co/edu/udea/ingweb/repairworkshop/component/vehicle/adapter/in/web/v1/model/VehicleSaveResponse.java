package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model;


import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleSaveResponse {

    private Long id;

    private Maker maker;

    private VehicleType vehicleType;

    private String model;

    private String licensePlate;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static VehicleSaveResponse fromModel(Vehicle vehicle){
        return VehicleSaveResponse.builder()
                .id(vehicle.getId()).maker(vehicle.getMaker())
                .vehicleType(vehicle.getVehicleType())
                .model(vehicle.getModel()).createdBy(vehicle.getCreatedBy())
                .createdAt(vehicle.getCreatedAt()).updatedAt(vehicle.getUpdatedAt())
                .licensePlate(vehicle.getLicensePlate())
                .updatedBy(vehicle.getUpdatedBy()).build();
    }
}
