package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.*;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddRepairLineToRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.RegisterRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.UpdateRepairStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
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
@RequestMapping(path = "/api/v1/repairs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(tags = {"Repairs"}, value = "Repairs")
public class RepairController {

    private final RegisterRepairUseCase registerRepairUseCase;

    private final GetRepairQuery getRepairQuery;

    private final AddRepairLineToRepairUseCase addRepairLineToRepairUseCase;

    private final UpdateRepairStateUseCase updateRepairStateUseCase;

    @PreAuthorize("hasRole('GG')")
    @PostMapping
    @ApiOperation(value = "Register a repair.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Registered."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid RepairSaveRequest repairToRegister){

        RepairSaveCmd repairToRegisterCmd = RepairSaveRequest.toModel(repairToRegister);

        Repair repairRegistered =
                registerRepairUseCase.register(repairToRegisterCmd);

        URI location = fromUriString("/api/v1/repairs").path("/{id}")
                .buildAndExpand(repairRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a repair by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = RepairSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<RepairSaveResponse> findById(@Valid @PathVariable("id") Long id){

        Repair repairFound = getRepairQuery.findById(id);

        return ResponseEntity.ok(RepairSaveResponse.fromModel(repairFound));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping
    @ApiOperation(value = "Find repairs by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepairListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<RepairListResponse> findByParameters(@Valid @NotNull RepairQuerySearchRequest queryCriteria,
                                                                   @PageableDefault(page = 0, size = 12,
                                                                 direction = Sort.Direction.DESC, sort = "id")
                                                                 Pageable pageable){

        RepairQuerySearchCmd queryCriteriaCmd = RepairQuerySearchRequest.toModel(queryCriteria);

        Page<Repair> repairsFound = getRepairQuery.findByParameters(queryCriteriaCmd, pageable);
        List<RepairListResponse> repairsFoundList = repairsFound.stream().map(RepairListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairsFoundList, repairsFound.getTotalElements(), repairsFound.getNumber(),
                repairsFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @PostMapping(path = "/{repairId}/repair-lines")
    @ApiOperation(value = "Add a repair line to a repair.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepairLineListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not acceptable.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<RepairLineListResponse> addRepairLineToRepair(@Valid @NotNull @PathVariable("repairId") Long repairId,
                                                                             @Valid @NotNull @RequestBody RepairLineSaveRequest repairLineToAdd){

        RepairLineSaveCmd repairLineToAddCmd = RepairLineSaveRequest.toModel(repairLineToAdd);
        repairLineToAddCmd.setRepairId(repairId);

        Set<RepairLine> repairLinesWithNewRepairLine =
                addRepairLineToRepairUseCase.addRepairLine(repairLineToAddCmd);

        List<RepairLineListResponse> repairLinesFoundList = repairLinesWithNewRepairLine
                .stream().map(RepairLineListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairLinesFoundList, 0, 0,
                repairLinesFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{repairId}/repair-lines")
    @ApiOperation(value = "Find repair lines by repair id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success.", response = RepairLineListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<RepairLineListResponse> findRepairLinesByRepairId(@Valid @NotNull @PathVariable("repairId")
                                                         Long repairId){

        Set<RepairLine> repairLinesFound = getRepairQuery.loadRepairLinesByRepairId(repairId);

        List<RepairLineListResponse> repairLinesFoundList = repairLinesFound
                .stream().map(RepairLineListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairLinesFoundList, 0, 0,
                repairLinesFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a repair.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = RepairSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<RepairSaveResponse> update(@RequestBody @NotNull @Valid RepairSaveRequest repairToUpdate,
                                       @Valid @PathVariable("id") @NotNull Long id){

        RepairSaveCmd repairToUpdateCmd = RepairSaveRequest.toModel(repairToUpdate);

        Repair repairUpdated =
                updateRepairStateUseCase.update(id, repairToUpdateCmd);

        return ResponseEntity.ok(RepairSaveResponse.fromModel(repairUpdated));
    }

}
