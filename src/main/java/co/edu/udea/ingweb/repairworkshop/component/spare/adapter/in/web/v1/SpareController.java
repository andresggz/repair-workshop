package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model.SpareListResponse;
import co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model.SpareQuerySearchRequest;
import co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model.SpareSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.spare.adapter.in.web.v1.model.SpareSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.RegisterSpareUseCase;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.UpdateSpareStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
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
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/spares", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(tags = {"Spares"}, value = "Spares")
public class SpareController {

    private final RegisterSpareUseCase registerSpareUseCase;

    private final GetSpareQuery getSpareQuery;

    private final UpdateSpareStateUseCase updateSpareStateUseCase;

    @PreAuthorize("hasRole('GG')")
    @PostMapping
    @ApiOperation(value = "Register a spare.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Registered."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid SpareSaveRequest spareToRegister){

        SpareSaveCmd spareToRegisterCmd = SpareSaveRequest.toModel(spareToRegister);

        Spare spareRegistered =
                registerSpareUseCase.register(spareToRegisterCmd);

        URI location = fromUriString("/api/v1/spares").path("/{id}")
                .buildAndExpand(spareRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a spare by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = SpareSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<SpareSaveResponse> findById(@Valid @PathVariable("id") Long id){

        Spare spareFound = getSpareQuery.findById(id);

        return ResponseEntity.ok(SpareSaveResponse.fromModel(spareFound));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping
    @ApiOperation(value = "Find spares by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SpareListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponsePagination<SpareListResponse> findByParameters(@Valid @NotNull SpareQuerySearchRequest queryCriteria,
                                                                  @PageableDefault(page = 0, size = 12,
                                                                 direction = Sort.Direction.DESC, sort = "id")
                                                                 Pageable pageable){

        SpareQuerySearchCmd queryCriteriaCmd = SpareQuerySearchRequest.toModel(queryCriteria);

        Page<Spare> sparesFound = getSpareQuery.findByParameters(queryCriteriaCmd, pageable);
        List<SpareListResponse> sparesFoundList = sparesFound.stream().map(SpareListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(sparesFoundList, sparesFound.getTotalElements(), sparesFound.getNumber(),
                sparesFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a spare.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = SpareSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<SpareSaveResponse> update(@RequestBody @NotNull @Valid SpareSaveRequest spareToUpdate,
                                         @Valid @PathVariable("id") @NotNull Long id){

        SpareSaveCmd spareToUpdateCmd = SpareSaveRequest.toModel(spareToUpdate);

        Spare spareUpdated = updateSpareStateUseCase.update(id, spareToUpdateCmd);

        return ResponseEntity.ok(SpareSaveResponse.fromModel(spareUpdated));
    }

}
