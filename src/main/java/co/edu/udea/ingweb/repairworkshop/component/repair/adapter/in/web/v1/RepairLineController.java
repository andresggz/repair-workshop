package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairLineSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairLineSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.SpareItemListResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.SpareItemSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddSpareItemToRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.FinishRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.StartRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.UpdateRepairLineStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
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
public class RepairLineController {

    private final StartRepairLineUseCase startRepairLineUseCase;

    private final FinishRepairLineUseCase finishRepairLineUseCase;

    private final AddSpareItemToRepairLineUseCase addSpareItemToRepairLineUseCase;

    private final UpdateRepairLineStateUseCase updateRepairLineStateUseCase;

    @PreAuthorize("hasRole('GG')")
    @PatchMapping(path = "/{id}/start")
    public ResponseEntity<RepairLineSaveResponse> start(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = startRepairLineUseCase.start(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    @PreAuthorize("hasRole('GG')")
    @PatchMapping(path = "/{id}/finish")
    public ResponseEntity<RepairLineSaveResponse> finish(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = finishRepairLineUseCase.finish(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    @PreAuthorize("hasRole('GG')")
    @PostMapping(path = "/{repair-line-id}/spare-items")
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
    @PutMapping(path = "/{id}")
    public ResponseEntity<RepairLineSaveResponse> update(@Valid @RequestBody @NotNull RepairLineSaveRequest repairLineToUpdate,
                                                         @Valid @PathVariable("id") @NotNull Long id){

        RepairLineSaveCmd repairLineToUpdateCmd = RepairLineSaveRequest.toModel(repairLineToUpdate);

        RepairLine repairLineUpdated = updateRepairLineStateUseCase.update(id, repairLineToUpdateCmd);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineUpdated));
    }
}
