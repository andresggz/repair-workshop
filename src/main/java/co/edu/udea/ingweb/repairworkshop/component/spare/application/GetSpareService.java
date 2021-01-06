package co.edu.udea.ingweb.repairworkshop.component.spare.application;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.LoadSparePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
class GetSpareService implements GetSpareQuery {

    private final LoadSparePort loadSparePort;

    @Override
    public Spare findById(@NotNull Long id) {

        Spare spareFound = loadSparePort.findById(id);

        return spareFound;
    }

    @Override
    public Page<Spare> findByParameters(@NotNull SpareQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Spare> sparesFound = loadSparePort.findByParameters(queryCriteria, pageable);

        return sparesFound;
    }
}
