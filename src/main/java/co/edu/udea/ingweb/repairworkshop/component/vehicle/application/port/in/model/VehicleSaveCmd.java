package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Maker;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.VehicleType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
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

    private Long userIdAuthenticated;

    public static Vehicle toModel(VehicleSaveCmd vehicleToRegister){
        return Vehicle.builder()
                .vehicleType(vehicleToRegister.getVehicleType())
                .model(vehicleToRegister.getModel())
                .build();
    }

}
