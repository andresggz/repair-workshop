package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairListResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleListResponse;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleQuerySearchRequest;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.in.web.v1.model.VehicleSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.GetVehicleQuery;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.RegisterVehicleUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.UpdateVehicleStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(tags = {"Vehicles"}, value = "Vehicles")
public class VehicleController {

    private final RegisterVehicleUseCase registerVehicleUseCase;

    private final GetVehicleQuery getVehicleQuery;

    private final UpdateVehicleStateUseCase updateVehicleStateUseCase;

    @PreAuthorize("hasRole('GG')")
    @PostMapping
    @ApiOperation(value = "Register a Vehicle.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Registered."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid VehicleSaveRequest vehicleToRegister){

        VehicleSaveCmd vehicleToRegisterCmd = VehicleSaveRequest.toModel(vehicleToRegister);

        Vehicle vehicleRegistered =
                registerVehicleUseCase.register(vehicleToRegisterCmd);

        URI location = fromUriString("/api/v1/vehicles").path("/{id}")
                .buildAndExpand(vehicleRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a vehicle by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = VehicleSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<VehicleSaveResponse> findById(@Valid @PathVariable("id") Long id){

        Vehicle vehicleFound = getVehicleQuery.findById(id);

        return ResponseEntity.ok(VehicleSaveResponse.fromModel(vehicleFound));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping
    @ApiOperation(value = "Find vehicles by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success.", response = VehicleListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
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

    @PreAuthorize("hasRole('GG')")
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a vehicle.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = VehicleSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<VehicleSaveResponse> update(@RequestBody @NotNull @Valid VehicleSaveRequest vehicleToUpdate,
                                                      @Valid @PathVariable("id") @NotNull Long id){

        VehicleSaveCmd vehicleToUpdateCmd = VehicleSaveRequest.toModel(vehicleToUpdate);

        Vehicle vehicleUpdated = updateVehicleStateUseCase.update(id, vehicleToUpdateCmd);

        return ResponseEntity.ok(VehicleSaveResponse.fromModel(vehicleUpdated));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}/repairs")
    @ApiOperation(value = "Find repairs by vehicle id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepairListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<RepairListResponse> findRepairsByVehicleId(@Valid @PathVariable("id") @NotNull Long id){

        Set<Repair> repairsFound = getVehicleQuery.findRepairsByVehicleId(id);

        List<RepairListResponse> repairsFoundList = repairsFound.stream().map(RepairListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairsFoundList, 0, 0,
                repairsFoundList.size());
    }

}
