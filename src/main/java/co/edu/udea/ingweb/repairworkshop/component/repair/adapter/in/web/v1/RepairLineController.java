package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model.RepairLineSaveResponse;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.FinishRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.StartRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.config.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/api/v1/repair-lines", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RepairLineController {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    private final StartRepairLineUseCase startRepairLineUseCase;

    private final FinishRepairLineUseCase finishRepairLineUseCase;

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PatchMapping(path = "/{id}/start")
    public ResponseEntity<RepairLineSaveResponse> start(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = startRepairLineUseCase.start(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PatchMapping(path = "/{id}/finish")
    public ResponseEntity<RepairLineSaveResponse> finish(@Valid @NotNull @PathVariable("id") Long id){

        RepairLine repairLineStarted = finishRepairLineUseCase.finish(id);

        return ResponseEntity.ok(RepairLineSaveResponse.from(repairLineStarted));
    }

    private Long getUserIdAuthenticated(HttpServletRequest request) {
        String token = jwtService.extractToken(request.getHeader(AUTHORIZATION));
        Long userIdAuthenticated = jwtService.userId(token);
        return userIdAuthenticated;
    }
}
