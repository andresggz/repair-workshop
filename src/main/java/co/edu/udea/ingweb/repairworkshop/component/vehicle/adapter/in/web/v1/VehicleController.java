package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleListResponse;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleQuerySearchRequest;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.GetVehicleQuery;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.RegisterVehicleUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import co.edu.udea.ingweb.repairworkshop.config.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VehicleController {

    private final RegisterVehicleUseCase registerVehicleUseCase;

    private final GetVehicleQuery getVehicleQuery;

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid VehicleSaveRequest vehicleToRegister, HttpServletRequest request){

        VehicleSaveCmd vehicleToRegisterCmd = VehicleSaveRequest.toModel(vehicleToRegister);

        vehicleToRegisterCmd.setUserIdAuthenticated(getUserIdAuthenticated(request));

        Vehicle vehicleRegistered =
                registerVehicleUseCase.register(vehicleToRegisterCmd);

        URI location = fromUriString("/api/v1/vehicles").path("/{id}")
                .buildAndExpand(vehicleRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<VehicleSaveResponse> findById(@Valid @PathVariable("id") Long id){

        Vehicle vehicleFound = getVehicleQuery.findById(id);

        return ResponseEntity.ok(VehicleSaveResponse.fromModel(vehicleFound));
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping
    public ResponsePagination<VehicleListResponse> findByParameters(@Valid @NotNull VehicleQuerySearchRequest queryCriteria,
                                                                    @PageableDefault(page = 0, size = 12,
                                                                 direction = Sort.Direction.DESC, sort = "id")
                                                                 Pageable pageable){

        VehicleQuerySearchCmd queryCriteriaCmd = VehicleQuerySearchRequest.toModel(queryCriteria);

        Page<Vehicle> vehiclesFound = getVehicleQuery.findByParameters(queryCriteriaCmd, pageable);
        List<VehicleListResponse> vehiclesFoundList = vehiclesFound.stream().map(VehicleListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(vehiclesFoundList, vehiclesFound.getTotalElements(), vehiclesFound.getNumber(),
                vehiclesFoundList.size());
    }

    private Long getUserIdAuthenticated(HttpServletRequest request) {
        String token = jwtService.extractToken(request.getHeader(AUTHORIZATION));
        Long userIdAuthenticated = jwtService.userId(token);
        return userIdAuthenticated;
    }


}
