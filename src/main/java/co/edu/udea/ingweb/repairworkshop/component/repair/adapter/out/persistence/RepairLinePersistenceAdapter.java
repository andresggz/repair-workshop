package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairLinePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
class RepairLinePersistenceAdapter implements LoadRepairLinePort, UpdateRepairLineStatePort {

    private static final String RESOURCE_NOT_FOUND = "RepairLine %d not found";

    private final SpringDataRepairLineRepository springDataRepairLineRepository;


    @Override
    public RepairLine loadById(@NotNull Long id) {

        RepairLine repairLineLoaded = springDataRepairLineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND, id)));

        return repairLineLoaded;
    }

    @Override
    public RepairLine update(@NotNull RepairLine repairLineToUpdate) {

        RepairLine repairLineToBeUpdated = repairLineToUpdate.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build();

        RepairLine repairLineUpdated = springDataRepairLineRepository.save(repairLineToBeUpdated);

        return repairLineUpdated;
    }
}
