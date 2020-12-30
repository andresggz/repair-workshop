package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.*;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddRepairLineToRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.RegisterRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.model.ResponsePagination;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/repairs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RepairController {

    private final RegisterRepairUseCase registerRepairUseCase;

    private final GetRepairQuery getRepairQuery;

    private final AddRepairLineToRepairUseCase addRepairLineToRepairUseCase;

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid RepairSaveRequest vehicleToRegister,
                                         HttpServletRequest request){

        RepairSaveCmd repairToRegisterCmd = RepairSaveRequest.toModel(vehicleToRegister);

        repairToRegisterCmd.setUserIdAuthenticated(getUserIdAuthenticated(request));

        Repair repairRegistered =
                registerRepairUseCase.register(repairToRegisterCmd);

        URI location = fromUriString("/api/v1/repairs").path("/{id}")
                .buildAndExpand(repairRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RepairSaveResponse> findById(@Valid @PathVariable("id") Long id){

        Repair repairFound = getRepairQuery.findById(id);

        return ResponseEntity.ok(RepairSaveResponse.fromModel(repairFound));
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping
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

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PostMapping(path = "/{repairId}/repair-lines")
    public ResponsePagination<RepairLineListResponse> addRepairLineToRepair(@Valid @NotNull @PathVariable("repairId") Long repairId,
                                                                             @Valid @NotNull @RequestBody RepairLineSaveRequest repairLineToAdd,
                                                                             HttpServletRequest request){

        RepairLineSaveCmd repairLineToAddCmd = RepairLineSaveRequest.toModel(repairLineToAdd);

        repairLineToAddCmd.setUserIdAuthenticated(getUserIdAuthenticated(request));
        repairLineToAddCmd.setRepairId(repairId);

        Set<RepairLine> repairLinesWithNewRepairLine =
                addRepairLineToRepairUseCase.addRepairLine(repairLineToAddCmd);

        List<RepairLineListResponse> repairLinesFoundList = repairLinesWithNewRepairLine
                .stream().map(RepairLineListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairLinesFoundList, 0, 0,
                repairLinesFoundList.size());
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping(path = "/{repairId}/repair-lines")
    public ResponsePagination<RepairLineListResponse> findRepairLinesByRepairId(@Valid @NotNull @PathVariable("repairId")
                                                         Long repairId){

        Set<RepairLine> repairLinesFound = getRepairQuery.findRepairLinesByRepairId(repairId);

        List<RepairLineListResponse> repairLinesFoundList = repairLinesFound
                .stream().map(RepairLineListResponse::fromModel)
                .collect(Collectors.toList());

        return ResponsePagination.fromObject(repairLinesFoundList, 0, 0,
                repairLinesFoundList.size());
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PatchMapping(path = "/{repairId}/repair-lines/start")

    private Long getUserIdAuthenticated(HttpServletRequest request) {
        String token = jwtService.extractToken(request.getHeader(AUTHORIZATION));
        Long userIdAuthenticated = jwtService.userId(token);
        return userIdAuthenticated;
    }


}
