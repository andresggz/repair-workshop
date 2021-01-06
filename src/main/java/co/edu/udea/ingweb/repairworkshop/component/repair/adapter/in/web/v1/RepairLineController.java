package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairLineSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairLineSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.SpareItemListResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.SpareItemSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.*;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemRemoveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/repair-lines", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Api(tags = {"Repair lines"}, value = "Repair lines")
public class RepairLineController {

    private final StartRepairLineUseCase startRepairLineUseCase;

    private final FinishRepairLineUseCase finishRepairLineUseCase;

    private final AddSpareItemToRepairLineUseCase addSpareItemToRepairLineUseCase;

    private final UpdateRepairLineStateUseCase updateRepairLineStateUseCase;

    private final RemoveSpareItemUseCase removeSpareItemUseCase;

    private final GetRepairLineQuery getRepairLineQuery;

    @PreAuthorize("hasRole('GG')")
    @PatchMapping(path = "/{id}/start")
    @ApiOperation(value = "Start a repair line service.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not acceptable.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<RepairLineSaveResponse> start(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = startRepairLineUseCase.start(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    @PreAuthorize("hasRole('GG')")
    @PatchMapping(path = "/{id}/finish")
    @ApiOperation(value = "Finish a repair line service.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not acceptable.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<RepairLineSaveResponse> finish(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = finishRepairLineUseCase.finish(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    @PreAuthorize("hasRole('GG')")
    @PostMapping(path = "/{repair-line-id}/spare-items")
    @ApiOperation(value = "Add a new spare item to a repair line.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not acceptable.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponsePagination<SpareItemListResponse> addSpareItemToRepairLine(@Valid @NotNull @RequestBody SpareItemSaveRequest spareItemToAdd,
                                                                       @Valid @NotNull @PathVariable("repair-line-id") Long repairLineId){

        SpareItemSaveCmd spareItemToAddCmd = SpareItemSaveRequest.toModel(spareItemToAdd);
        spareItemToAddCmd.setRepairLineId(repairLineId);

        Set<SpareItem> spareItemsWithNewSpareItem = addSpareItemToRepairLineUseCase.addSpareItem(spareItemToAddCmd);

        List<SpareItemListResponse> spareItemsAdded = spareItemsWithNewSpareItem.stream()
                .map(SpareItemListResponse::fromModel).collect(Collectors.toList());

        return ResponsePagination.fromObject(spareItemsAdded, 0, 0, spareItemsAdded.size());
    }

    @PreAuthorize("hasRole('GG')")
    @DeleteMapping(path = "/{repair-line-id}/spare-items/{spare-item-id}")
    @ApiOperation(value = "Remove a spare item from a repair line.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 406, message = "Not acceptable.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponsePagination<SpareItemListResponse> removeSpareItemFromRepairLine(@Valid @PathVariable("spare-item-id") Long spareItemId,
                                                                              @Valid @NotNull @PathVariable("repair-line-id") Long repairLineId){

        SpareItemRemoveCmd spareItemToRemoveCmd = SpareItemRemoveCmd.builder()
                .spareItemId(spareItemId).repairLineId(repairLineId)
                .build();


        Set<SpareItem> spareItemsWithoutSpareItemRemoved = removeSpareItemUseCase.remove(spareItemToRemoveCmd);

        List<SpareItemListResponse> spareItemsAdded = spareItemsWithoutSpareItemRemoved.stream()
                .map(SpareItemListResponse::fromModel).collect(Collectors.toList());

        return ResponsePagination.fromObject(spareItemsAdded, 0, 0, spareItemsAdded.size());
    }

    @PreAuthorize("hasRole('GG')")
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a repair line.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = RepairLineSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<RepairLineSaveResponse> update(@Valid @RequestBody @NotNull RepairLineSaveRequest repairLineToUpdate,
                                                         @Valid @PathVariable("id") @NotNull Long id){

        RepairLineSaveCmd repairLineToUpdateCmd = RepairLineSaveRequest.toModel(repairLineToUpdate);

        RepairLine repairLineUpdated = updateRepairLineStateUseCase.update(id, repairLineToUpdateCmd);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineUpdated));
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{repair-line-id}/spare-items")
    @ApiOperation(value = "Find spare items by repair line id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponsePagination<SpareItemListResponse> findSpareItemsByRepairLineId(@Valid @NotNull @PathVariable("repair-line-id") Long repairLineId){

        Set<SpareItem> spareItemsFound = getRepairLineQuery.findSpareItemsByRepairLineId(repairLineId);

        List<SpareItemListResponse> spareItemsFoundList = spareItemsFound.stream()
                .map(SpareItemListResponse::fromModel).collect(Collectors.toList());

        return ResponsePagination.fromObject(spareItemsFoundList, 0, 0, spareItemsFoundList.size());
    }

    @PreAuthorize("hasRole('GG')")
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find repair line by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)
    })
    public ResponseEntity<RepairLineSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id){

       RepairLine repairLineFound = getRepairLineQuery.findById(id);

       return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineFound));
    }
}
