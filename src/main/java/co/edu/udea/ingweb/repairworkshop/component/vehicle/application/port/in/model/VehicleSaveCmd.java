package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleSaveCmd {

    @NotNull
    private Maker maker;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String model;

    @NotEmpty
    private Set<Long> ownerIds = new HashSet<>();

    @NotNull
    @NotBlank
    private String licensePlate;

    public static Vehicle toModel(VehicleSaveCmd vehicleToRegister){
        return Vehicle.builder()
                .maker(vehicleToRegister.getMaker())
                .vehicleType(vehicleToRegister.getVehicleType())
                .model(vehicleToRegister.getModel())
                .licensePlate(vehicleToRegister.getLicensePlate())
                .build();
    }

}
